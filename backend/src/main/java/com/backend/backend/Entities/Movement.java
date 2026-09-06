package com.backend.backend.Entities;

import com.backend.backend.Datatypes.MuscleGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "movements")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progression_id")
    private Progression progression;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(name = "muscle_group")
    private MuscleGroup muscleGroup;
}
