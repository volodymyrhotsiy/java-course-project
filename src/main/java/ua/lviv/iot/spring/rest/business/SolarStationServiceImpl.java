package ua.lviv.iot.spring.rest.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.spring.rest.dataaccess.SolarStationRepository;
import ua.lviv.iot.spring.rest.model.Battery;
import ua.lviv.iot.spring.rest.model.Panel;
import ua.lviv.iot.spring.rest.model.SolarStation;

import java.util.List;

@Service
public class SolarStationServiceImpl implements SolarStationService {

    @Autowired
    private SolarStationRepository solarStationRepository;

    @Override
    public List<SolarStation> getAllSolarStations() {
        return solarStationRepository.getAllSolarStations();
    }

    @Override
    public SolarStation getSolarStationById(Integer solarStationId) {
        return solarStationRepository.getSolarStationById(solarStationId);
    }

    @Override
    public void createSolarStation(SolarStation solarStation) {
        solarStationRepository.createSolarStation(solarStation);
    }

    @Override
    public void deleteSolarStation(Integer solarStationId) {
        solarStationRepository.deleteSolarStation(solarStationId);
    }

    @Override
    public boolean doesSolarStationExists(Integer solarStationId) {
        return solarStationRepository.isSolarStationExists(solarStationId);
    }

    @Override
    public void updateSolarStation(Integer solarStationId, SolarStation updatedSolarStation) {
        solarStationRepository.updateSolarStation(solarStationId, updatedSolarStation);
    }

    @Override
    public List<Panel> getPanelsForSolarStation(Integer solarStationId) {
        SolarStation solarStation = solarStationRepository.getSolarStationById(solarStationId);
        if (solarStation != null) {
            return solarStation.getPanels();
        }
        return null;
    }

    @Override
    public List<Battery> getBatteriesForSolarStation(Integer solarStationId) {
        SolarStation solarStation = solarStationRepository.getSolarStationById(solarStationId);
        if (solarStation != null) {
            return solarStation.getBatteries();
        }
        return null;
    }
}
