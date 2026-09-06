package com.backend.backend.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Entity
@Table(name = "progression")
public class Progression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private Date date;

    @Setter
    @Column(nullable = false)
    private Integer sets;

    @Setter
    @Column(nullable = false)
    private Integer reps;

    @Setter
    @Column(nullable = false)
    private Integer weight;

    @Setter
    @Version
    private Long version;
}
