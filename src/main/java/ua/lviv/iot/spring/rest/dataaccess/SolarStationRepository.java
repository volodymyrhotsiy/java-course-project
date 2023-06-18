package ua.lviv.iot.spring.rest.dataaccess;

import org.springframework.stereotype.Repository;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class SolarStationRepository {
    private Map<Integer, SolarStation> solarStations = new HashMap<>();
    private AtomicInteger idCounter = new AtomicInteger();

    public List<SolarStation> getAllSolarStations() {
        return new LinkedList<>(solarStations.values());
    }

    public SolarStation getSolarStationById(Integer solarStationId) {
        return solarStations.get(solarStationId);
    }

    public void createSolarStation(SolarStation solarStation) {
        solarStation.setId(idCounter.incrementAndGet());
        solarStations.put(solarStation.getId(), solarStation);
    }

    public void deleteSolarStation(Integer solarStationId) {
        solarStations.remove(solarStationId);
    }

    public boolean isSolarStationExists(Integer solarStationId) {
        return solarStations.containsKey(solarStationId);
    }

    public void updateSolarStation(Integer solarStationId, SolarStation updatedSolarStation) {
        updatedSolarStation.setId(solarStationId);
        solarStations.put(solarStationId, updatedSolarStation);
    }
}
