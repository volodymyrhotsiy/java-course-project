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
            reader.readLine();

            rowData = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowData;
    }

    public <T> Map<Integer, T> readObjectsFromCSV(String fileName, Class<T> objectClass) {
        Map<Integer, T> objects = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] headers = null;
            int id = 1;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (headers == null) {
                    headers = data;
                } else if (data.length == headers.length) {
                    T object = createObject(data, objectClass);
                    if (object != null) {
                        objects.put(id, object);
                        id++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    private <T> T createObject(String[] data, Class<T> objectClass) {
        if (objectClass == SolarStation.class) {
            int id = Integer.parseInt(data[0]);
            String address = data[1];
            List<Panel> panels = parsePanels(data[2]);
            List<Battery> batteries = parseBatteries(data[3]);
            return objectClass.cast(new SolarStation(id, address, panels, batteries));
        } else {
            return null;
        }
    }

    private List<Panel> parsePanels(String data) {
        List<Panel> panels = new ArrayList<>();
        String[] panelStrings = data.substring(1, data.length() - 1).split(",");

        for (String panelString : panelStrings) {
            String[] panelData = panelString.split("\\(");
            int id = Integer.parseInt(panelData[0]);
            Panel panel = new Panel();
            panel.setId(id);
            panel.setSolarStation(null);
            panel.setType(null);
            panel.setPower(0.0);
            panel.setTiltAngle(0.0);
            panels.add(panel);
        }

        return panels;
    }

    private List<Battery> parseBatteries(String data) {
        List<Battery> batteries = new ArrayList<>();
        String[] batteryStrings = data.substring(1, data.length() - 1).split(",");

        for (String batteryString : batteryStrings) {
            String[] batteryData = batteryString.split("\\(");
            int id = Integer.parseInt(batteryData[0]);
            Battery battery = new Battery();
            battery.setId(id);
            battery.setSolarStation(null);
            battery.setCapacity(0.0);
            battery.setUsageDuration(0.0);
            batteries.add(battery);
        }

        return batteries;
    }
}