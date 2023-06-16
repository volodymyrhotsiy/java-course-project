package ua.lviv.iot.spring.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.spring.rest.business.BatteryServiceImpl;
import ua.lviv.iot.spring.rest.model.Battery;

import java.util.List;

@RequestMapping("/batteries")
@RestController
public class BatteryController {

    @Autowired
    private BatteryServiceImpl batteryService;

    @GetMapping
    public List<Battery> getBatteries() {
        return batteryService.getAllBatteries();
    }

    @GetMapping("/{id}")
    public Battery getBattery(@PathVariable("id") Integer batteryId) {
        return batteryService.getBatteryById(batteryId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Battery createBattery(@RequestBody Battery battery) {
        batteryService.createBattery(battery);
        return battery;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBattery(@PathVariable("id") Integer batteryId) {
        if (batteryService.doesBatteryExists(batteryId)) {
            batteryService.deleteBattery(batteryId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public Battery updateBattery(@PathVariable("id") Integer batteryId, @RequestBody Battery updatedBattery) {
        if (batteryService.doesBatteryExists(batteryId)) {
            batteryService.updateBattery(batteryId, updatedBattery);
            return updatedBattery;
        } else {
            return null;
        }
    }
}
