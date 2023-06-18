package ua.lviv.iot.spring.rest.dataaccess;

import org.springframework.stereotype.Repository;
import ua.lviv.iot.spring.rest.model.Panel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PanelRepository {
    private Map<Integer, Panel> panels = new HashMap<>();
    private AtomicInteger idCounter = new AtomicInteger();

    public List<Panel> getAllPanels() {
        return new LinkedList<>(panels.values());
    }

    public Panel getPanelById(Integer panelId) {
        return panels.get(panelId);
    }

    public void createPanel(Panel panel) {
        panel.setId(idCounter.incrementAndGet());
        panels.put(panel.getId(), panel);
    }

    public void deletePanel(Integer panelId) {
        panels.remove(panelId);
    }

    public boolean isPanelExists(Integer panelId) {
        return panels.containsKey(panelId);
    }

    public void updatePanel(Integer panelId, Panel updatedPanel) {
        updatedPanel.setId(panelId);
        panels.put(panelId, updatedPanel);
    }
}