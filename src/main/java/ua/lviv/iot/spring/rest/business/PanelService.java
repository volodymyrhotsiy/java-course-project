package ua.lviv.iot.spring.rest.business;


import ua.lviv.iot.spring.rest.model.Panel;

import java.util.List;

public interface PanelService {
    List<Panel> getAllPanels();

    Panel getPanelById(Integer panelId);

    void createPanel(Panel panel);

    void deletePanel(Integer panelId);

    boolean doesPanelExists(Integer panelId);

    void updatePanel(Integer panelId, Panel updatedPanel);
}
