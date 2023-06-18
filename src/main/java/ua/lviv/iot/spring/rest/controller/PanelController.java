package ua.lviv.iot.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.spring.rest.business.PanelServiceImpl;
import ua.lviv.iot.spring.rest.model.Panel;

import java.util.List;

@RequestMapping("/panels")
@RestController
public class PanelController {

    @Autowired
    private PanelServiceImpl panelService;

    @GetMapping
    public List<Panel> getPanels() {
        return panelService.getAllPanels();
    }

    @GetMapping("/{id}")
    public Panel getPanel(@PathVariable("id") Integer panelId) {
        return panelService.getPanelById(panelId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Panel createPanel(@RequestBody Panel panel) {
        panelService.createPanel(panel);
        return panel;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanel(@PathVariable("id") Integer panelId) {
        if (panelService.doesPanelExists(panelId)) {
            panelService.deletePanel(panelId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public Panel updatePanel(@PathVariable("id") Integer panelId, @RequestBody Panel updatedPanel) {
        if (panelService.doesPanelExists(panelId)) {
            panelService.updatePanel(panelId, updatedPanel);
            return updatedPanel;
        } else {
            return null;
        }
    }
}