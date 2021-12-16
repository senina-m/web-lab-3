package ru.senina.itmo.web.web_lab_3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Named
@SessionScoped
@AllArgsConstructor @Getter @Setter
public class Coordinates implements Serializable {
    private double x = 0.0;
    private double y = 0.0;
    private double r = 0.0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coordinates_id")
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "coordinates_id")
    private Attempt attempt;

    public Coordinates(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.id = null;
        this.attempt = null;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "(x=" + x +
                "; y=" + y +
                "; r=" + r +
                "), id=" + id +
                ", attempts isn't null=" + (Objects.nonNull(attempt)) +
                '}';
    }
}