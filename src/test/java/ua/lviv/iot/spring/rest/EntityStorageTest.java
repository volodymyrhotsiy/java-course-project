package ua.lviv.iot.spring.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.spring.rest.model.Battery;
import ua.lviv.iot.spring.rest.model.Panel;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityStorageTest {
    private static final String TEST_FILE_NAME = "test.csv";
    private EntityStorage entityStorage;

    @BeforeEach
    public void setup() {
        entityStorage = new EntityStorage("SolarStation");
        entityStorage.setFileName(TEST_FILE_NAME);
        clearTestFile();
    }
    @AfterEach
    public void cleanup() {
        clearTestFile();
    }
    @Test
    public void testSaveEntityData() {
        SolarStation solarStation = new SolarStation(1, "address1",null,null);
        entityStorage.saveEntityData(solarStation);

        String expectedRow = "1,address1,,";
        String rowData = entityStorage.readEntityData();
        Assertions.assertEquals(expectedRow, rowData);
    }

    @Test
    public void testReadEntityData() {
        SolarStation solarStation = new SolarStation(1, "address1",null,null);
        entityStorage.saveEntityData(solarStation);
        String expectedRow = "1,address1,,";
        String rowData = entityStorage.readEntityData();
        Assertions.assertEquals(expectedRow, rowData);
    }

    @Test
    public void testReadObjectsFromCSV() {
        Panel panel = new Panel();
        Battery battery = new Battery();
        List<Panel> panels = new ArrayList<>();
        panels.add(panel);
        List<Battery> batteries = new ArrayList<>();
        batteries.add(battery);
        SolarStation solarStation = new SolarStation(1, "address1",panels,batteries);
        entityStorage.saveEntityData(solarStation);
        Map<Integer, SolarStation> myMap = entityStorage.readObjectsFromCSV();

        Assertions.assertEquals(solarStation, myMap.values());
    }

    private void clearTestFile() {
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTestDataToTestFile(String data) {
        try {
            Files.writeString(Paths.get(TEST_FILE_NAME), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}