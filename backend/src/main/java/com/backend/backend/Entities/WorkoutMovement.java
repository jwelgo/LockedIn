package com.backend.backend.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "workout_movements")
public class WorkoutMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id", nullable = false)
    private Movement movement;

    @Column(name = "sort_order")
    private Integer sortOrder;   // 1st exercise, 2nd exercise, etc.

    private Integer sets;
    private Integer reps;
    private Double weight;
}
