    package ua.lviv.iot.spring.rest.model;

    import java.util.List;

    import jakarta.persistence.*;
    import lombok.*;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    public class SolarStation {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;
        private String address;
        @OneToMany(mappedBy = "solarStation", cascade = CascadeType.ALL)
        private List<Panel> panels;

        @OneToMany(mappedBy = "solarStation", cascade = CascadeType.ALL)
        private List<Battery> batteries;

        public List<Panel> getPanels() {
            return panels;
        }

        public List<Battery> getBatteries() {
            return batteries;
        }
    }
