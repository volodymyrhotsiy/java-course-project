    package ua.lviv.iot.spring.rest.model;

    import jakarta.persistence.*;
    import lombok.*;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    public class Panel {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;
        @ManyToOne
        private SolarStation solarStation;
        private String type;
        private double power;
        private double tiltAngle;
    }
