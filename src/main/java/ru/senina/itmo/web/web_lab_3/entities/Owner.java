package ru.senina.itmo.web.web_lab_3.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity @Getter @Setter
public class Owner {
    @Id
    @Column(name = "userSessionId", nullable = false)
    private String sessionId;
    //if we want to drop all attempts after owner death

    @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Attempt> attemptList;

    public Owner initOwner(){
        Owner owner = new Owner();
        owner.setAttemptList(new ArrayList<>());
        return owner;
    }
}
