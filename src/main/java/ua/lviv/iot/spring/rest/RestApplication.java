package ua.lviv.iot.spring.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class RestApplication {

    private static final String ENTITY_NAME = "SolarStation";

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
        Map<Integer, SolarStation> entityMap = readEntitiesFromCurrentMonth();
    }

    private static Map<Integer, SolarStation> readEntitiesFromCurrentMonth() {
        String currentMonth = getCurrentMonthString();
        File[] files = getFilesInCurrentMonth(currentMonth);

        Map<Integer, SolarStation> entityMap = new HashMap<>();

        for (File file : files) {
            EntityStorage entityStorage = new EntityStorage(ENTITY_NAME);
            Map<Integer, SolarStation> entities = entityStorage.readObjectsFromCSV();
            entityMap.putAll(entities);
        }

        return entityMap;
    }

    private static String getCurrentMonthString() {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        return currentMonth.name().toLowerCase();
    }

    private static File[] getFilesInCurrentMonth(String currentMonth) {
        File directory = new File(".");
        return directory.listFiles((dir, name) -> name.toLowerCase().endsWith("-" + currentMonth + ".csv"));
    }
}

