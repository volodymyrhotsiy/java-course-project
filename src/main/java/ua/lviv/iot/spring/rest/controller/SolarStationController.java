package ua.lviv.iot.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.spring.rest.business.SolarStationServiceImpl;
import ua.lviv.iot.spring.rest.model.Battery;
import ua.lviv.iot.spring.rest.model.Panel;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.util.List;

@RequestMapping("/solar_stations")
@RestController
public class SolarStationController {

    @Autowired
    private SolarStationServiceImpl solarStationService;
    @GetMapping
    public List<SolarStation> getSolarStations() {
        return solarStationService.getAllSolarStations();
    }
    @GetMapping("/{id}")
    public SolarStation getSolarStation(@PathVariable("id") Integer solarStationId) {
        return solarStationService.getSolarStationById(solarStationId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SolarStation createSolarStation(@RequestBody SolarStation solarStation) {
        solarStationService.createSolarStation(solarStation);
        return solarStation;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolarStation(@PathVariable("id") Integer solarStationId) {
        if (solarStationService.doesSolarStationExists(solarStationId)) {
            solarStationService.deleteSolarStation(solarStationId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public SolarStation updateSolarStation(@PathVariable("id") Integer solarStationId, @RequestBody SolarStation updatedSolarStation) {
        if (solarStationService.doesSolarStationExists(solarStationId)) {
            solarStationService.updateSolarStation(solarStationId, updatedSolarStation);
            return updatedSolarStation;
        } else {
            return null;
        }
    }

    @GetMapping("/{id}/panels")
    public List<Panel> getPanelsForSolarStation(@PathVariable("id") Integer solarStationId) {
        return solarStationService.getPanelsForSolarStation(solarStationId);
    }

    @GetMapping("/{id}/batteries")
    public List<Battery> getBatteriesForSolarStation(@PathVariable("id") Integer solarStationId) {
        return solarStationService.getBatteriesForSolarStation(solarStationId);
    }
}