package ua.lviv.iot.spring.rest.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.spring.rest.dataaccess.PanelRepository;
import ua.lviv.iot.spring.rest.model.Panel;

import java.util.List;

@Service
public class PanelServiceImpl implements PanelService {

    @Autowired
    private PanelRepository panelRepository;

    @Override
    public List<Panel> getAllPanels() {
        return panelRepository.getAllPanels();
    }

    @Override
    public Panel getPanelById(Integer panelId) {
        return panelRepository.getPanelById(panelId);
    }

    @Override
    public void createPanel(Panel panel) {
        panelRepository.createPanel(panel);
    }

    @Override
    public void deletePanel(Integer panelId) {
        panelRepository.deletePanel(panelId);
    }

    @Override
    public boolean doesPanelExists(Integer panelId) {
        return panelRepository.isPanelExists(panelId);
    }

    @Override
    public void updatePanel(Integer panelId, Panel updatedPanel) {
        panelRepository.updatePanel(panelId, updatedPanel);
    }
}