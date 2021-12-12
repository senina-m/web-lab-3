package ru.senina.itmo.web.web_lab_3.entities;

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
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;
    private boolean doFitArea;
    @Id
    private Long id;

    @ManyToOne()
    private Owner owner;

    @Deprecated
    public Attempt(Coordinates coordinates, boolean check) {
        this.coordinates = coordinates;
        this.doFitArea = check;
        this.owner = null;
        this.id = null;
    }

    public static Attempt initAttempt() {
        Attempt attempt = new Attempt();
        attempt.setCoordinates(new Coordinates());
        attempt.setOwner(new Owner());
        return attempt;
    }
}
