package ua.lviv.iot.spring.rest.business;

import ua.lviv.iot.spring.rest.model.Battery;
import ua.lviv.iot.spring.rest.model.Panel;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.util.List;

public interface SolarStationService {
    List<SolarStation> getAllSolarStations();

    SolarStation getSolarStationById(Integer solarStationId);

    void createSolarStation(SolarStation solarStation);

    void deleteSolarStation(Integer solarStationId);

    boolean doesSolarStationExists(Integer solarStationId);

    void updateSolarStation(Integer solarStationId, SolarStation updatedSolarStation);

    List<Panel> getPanelsForSolarStation(Integer solarStationId);

    List<Battery> getBatteriesForSolarStation(Integer solarStationId);
}
