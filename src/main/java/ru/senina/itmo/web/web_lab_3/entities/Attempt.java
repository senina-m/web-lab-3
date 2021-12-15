package ru.senina.itmo.web.web_lab_3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Attempt implements Serializable {

    @OneToOne(mappedBy = "attempt", cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;
    private boolean doFitArea;

    @Id @PrimaryKeyJoinColumn
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Owner owner;

    public Attempt(Coordinates coordinates, boolean check) {
        this.coordinates = coordinates;
        this.doFitArea = check;
        this.owner = null;
        this.id = null;
    }

    public static Attempt initAttempt() {
        Attempt attempt = new Attempt();
        attempt.setCoordinates(new Coordinates());
        attempt.setOwner(Owner.initOwner());
        return attempt;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "coordinates: (x=" + coordinates.getX() +
                "; y=" + coordinates.getY() +
                "; r=" + coordinates.getR() +
                "), doFitArea=" + doFitArea +
                ", id=" + id +
                ", owner isn't null=" + (Objects.nonNull(owner)) +
                '}';
    }
}
