package ua.lviv.iot.spring.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Test
    public void testSaveEntityData() {
        SolarStation solarStation = new SolarStation(1, "address1",null,null);
        entityStorage.saveEntityData(solarStation);

        String expectedRow = "1,address1,[],[]";
        String rowData = entityStorage.readEntityData();
        Assertions.assertEquals(expectedRow, rowData);
    }

    @Test
    public void testReadEntityData() {
        writeTestDataToTestFile("1,address1,[],[]");

        String expectedRow = "1,address1,[],[]";
        String rowData = entityStorage.readEntityData();
        Assertions.assertEquals(expectedRow, rowData);
    }

    @Test
    public void testReadObjectsFromCSV() {
        writeTestDataToTestFile("1,address1,[],[]\n2,address2,[],[]");

        Map<Integer, SolarStation> objects = entityStorage.readObjectsFromCSV(TEST_FILE_NAME, SolarStation.class);
        Assertions.assertEquals(2, objects.size());

        SolarStation solarStation1 = objects.get(1);
        Assertions.assertNotNull(solarStation1);
        Assertions.assertEquals(1, solarStation1.getId());
        Assertions.assertEquals("address1", solarStation1.getAddress());

        SolarStation solarStation2 = objects.get(2);
        Assertions.assertNotNull(solarStation2);
        Assertions.assertEquals(2, solarStation2.getId());
        Assertions.assertEquals("address2", solarStation2.getAddress());
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