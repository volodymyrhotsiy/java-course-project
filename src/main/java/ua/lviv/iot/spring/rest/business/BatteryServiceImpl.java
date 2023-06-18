package ua.lviv.iot.spring.rest.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.spring.rest.dataaccess.BatteryRepository;
import ua.lviv.iot.spring.rest.model.Battery;

import java.util.List;

@Service
public class BatteryServiceImpl implements BatteryService {

    @Autowired
    private BatteryRepository batteryRepository;

    @Override
    public List<Battery> getAllBatteries() {
        return batteryRepository.getAllBatteries();
    }

    @Override
    public Battery getBatteryById(Integer batteryId) {
        return batteryRepository.getBatteryById(batteryId);
    }

    @Override
    public void createBattery(Battery battery) {
        batteryRepository.createBattery(battery);
    }

    @Override
    public void deleteBattery(Integer batteryId) {
        batteryRepository.deleteBattery(batteryId);
    }

    @Override
    public boolean doesBatteryExists(Integer batteryId) {
        return batteryRepository.isBatteryExists(batteryId);
    }

    @Override
    public void updateBattery(Integer batteryId, Battery updatedBattery) {
        batteryRepository.updateBattery(batteryId, updatedBattery);
    }
}
