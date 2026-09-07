package com.backend.backend.Entities;

import com.backend.backend.Datatypes.WorkoutType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutMovement> workoutMovements = new ArrayList<>();

    @Column
    private WorkoutType type;

    @Column
    private Date date;

    @Column
    private String notes;
}