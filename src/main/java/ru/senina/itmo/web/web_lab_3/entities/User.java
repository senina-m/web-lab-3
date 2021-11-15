package ru.senina.itmo.web.web_lab_3.entities;

import javax.persistence.*;
import java.util.List;


@Entity
public class User {

    @Id
    @Column(name = "userSessionId", nullable = false)
    private Long sessionId;

    // orphanRemoval = true - если мы хотим убивать все LabWork когда умер user
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Attempt> attemptList;
}
