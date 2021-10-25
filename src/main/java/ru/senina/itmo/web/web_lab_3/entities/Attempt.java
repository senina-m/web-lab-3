package ru.senina.itmo.web.web_lab_3.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

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
    private User user;

    public Attempt(Coordinates coordinates, boolean check) {
        this.coordinates = coordinates;
        this.doFitArea = check;
        this.user = null;
        this.id = null;
    }
}
