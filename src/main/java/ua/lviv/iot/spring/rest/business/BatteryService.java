package ua.lviv.iot.spring.rest.business;

import ua.lviv.iot.spring.rest.model.Battery;

import java.util.List;

public interface BatteryService {
    List<Battery> getAllBatteries();

    Battery getBatteryById(Integer batteryId);

    void createBattery(Battery battery);

    void deleteBattery(Integer batteryId);

    boolean doesBatteryExists(Integer batteryId);

    void updateBattery(Integer batteryId, Battery updatedBattery);
}