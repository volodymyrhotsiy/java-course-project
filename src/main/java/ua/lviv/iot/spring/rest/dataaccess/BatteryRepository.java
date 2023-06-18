package ua.lviv.iot.spring.rest.dataaccess;


import org.springframework.stereotype.Repository;
import ua.lviv.iot.spring.rest.model.Battery;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BatteryRepository {
    private Map<Integer, Battery> batteries = new HashMap<>();
    private AtomicInteger idCounter = new AtomicInteger();

    public List<Battery> getAllBatteries() {
        return new LinkedList<>(batteries.values());
    }

    public Battery getBatteryById(Integer batteryId) {
        return batteries.get(batteryId);
    }

    public void createBattery(Battery battery) {
        battery.setId(idCounter.incrementAndGet());
        batteries.put(battery.getId(), battery);
    }

    public void deleteBattery(Integer batteryId) {
        batteries.remove(batteryId);
    }

    public boolean isBatteryExists(Integer batteryId) {
        return batteries.containsKey(batteryId);
    }

    public void updateBattery(Integer batteryId, Battery updatedBattery) {
        updatedBattery.setId(batteryId);
        batteries.put(batteryId, updatedBattery);
    }
}