package com.emsi.patientsapp.entities;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nom;
@Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private boolean malade;
    private int score;

    public Patient(Object o, String hassan, Date date, boolean b, int i) {
        this.id=o;
    }
}
