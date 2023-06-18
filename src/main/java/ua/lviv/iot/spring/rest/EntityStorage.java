package ua.lviv.iot.spring.rest;

import lombok.Getter;
import lombok.Setter;
import ua.lviv.iot.spring.rest.model.Battery;
import ua.lviv.iot.spring.rest.model.Panel;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class EntityStorage {
    private String entityName;
    private String fileName;

    public EntityStorage(String entityName) {
        this.entityName = entityName;
        this.fileName = entityName.toLowerCase() + "-" + getCurrentDate() + ".csv";
    }

    public void saveEntityData(Object entity) {
        Class<?> entityClass = entity.getClass();
        List<String> headers = getHeaders(entityClass);
        List<String> data = getData(entity, entityClass);
        createFileIfNotExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            if (isFileEmpty()) {
                writer.println(String.join(",", headers));
            }
            writer.println(String.join(",", data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getHeaders(Class<?> entityClass) {
        List<String> headers = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            headers.add(field.getName());
        }
        return headers;
    }

    private List<String> getData(Object entity, Class<?> entityClass) {
        List<String> data = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                data.add(value != null ? value.toString() : "");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private void createFileIfNotExists() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                    Class<?> entityClass = Class.forName(entityName);
                    List<String> headers = getHeaders(entityClass);
                    writer.println(String.join(",", headers));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isFileEmpty() {
        File file = new File(fileName);
        return file.length() == 0;
    }

    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

    public String readEntityData() {
        String rowData = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Skip the headers row
            reader.readLine();

            // Read the next line (data row)
            rowData = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowData;
    }

    public  Map<Integer, SolarStation> readObjectsFromCSV() {
        Map<Integer, SolarStation> objects = new HashMap<>();
        String[] values = readEntityData().split(",");

        SolarStation solarStation = createSolarStation(values);

        objects.put(1,solarStation);

        return objects;
    }

    private SolarStation createSolarStation(String[] data) {
        int id = Integer.parseInt(data[0]);
        String address = data[1];
        String panelsData = data[2];
        String batteriesData = data[3];

        List<Panel> panels = parsePanels(panelsData);
        List<Battery> batteries = parseBatteries(batteriesData);

        SolarStation solarStation = new SolarStation(id, address,panels,batteries);
        for (Panel panel : panels) {
            panel.setSolarStation(solarStation);
        }
        for (Battery battery : batteries) {
            battery.setSolarStation(solarStation);
        }

        return solarStation;
    }

    private List<Panel> parsePanels(String data) {
        List<Panel> panels = new ArrayList<>();
        String[] panelStrings = data.split(",");

        for (String panelString : panelStrings) {
            panelString = panelString.replaceAll("\\[|\\]", "");
            Panel panel = createPanel(panelString);
            panels.add(panel);
        }

        return panels;
    }

    private List<Battery> parseBatteries(String data) {
        List<Battery> batteries = new ArrayList<>();
        String[] batteryStrings = data.split("\\], \\[");

        for (String batteryString : batteryStrings) {
            batteryString = batteryString.replaceAll("\\[|\\]", "");
            Battery battery = createBattery(batteryString);
            batteries.add(battery);
        }

        return batteries;
    }
    private Panel createPanel(String data) {
        String[] panelData = data.split(", ");

        int id = Integer.parseInt(panelData[0].split("=")[1]);
        String type = panelData[1].split("=")[1];
        double power = Double.parseDouble(panelData[2].split("=")[1]);
        double tiltAngle = Double.parseDouble(panelData[3].split("=")[1]);

        Panel panel = new Panel();
        panel.setId(id);
        panel.setType(type);
        panel.setSolarStation(null);
        panel.setPower(power);
        panel.setTiltAngle(tiltAngle);

        return panel;
    }
    private Battery createBattery(String data) {
        String[] batteryData = data.split(", ");

        int id = Integer.parseInt(batteryData[0].split("=")[1]);
        double capacity = Double.parseDouble(batteryData[1].split("=")[1]);
        double usageDuration = Double.parseDouble(batteryData[2].split("=")[1]);

        Battery battery = new Battery();
        battery.setId(id);
        battery.setSolarStation(null);
        battery.setCapacity(capacity);
        battery.setUsageDuration(usageDuration);

        return battery;
    }
}