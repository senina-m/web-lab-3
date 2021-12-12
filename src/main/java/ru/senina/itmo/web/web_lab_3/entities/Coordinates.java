package ru.senina.itmo.web.web_lab_3.entities;

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
    private double x;
    private double y;
    private double r;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coordinates_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "coordinates_id")
    private Attempt attempt;

    @Deprecated
    public Coordinates(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.id = null;
        this.attempt = null;
    }
}