package com.backend.backend.Repositories;

import com.backend.backend.Entities.WorkoutMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutMovementRepository extends JpaRepository<WorkoutMovement, Long> {
    List<WorkoutMovement> findByMovementIdOrderByWorkoutDateAsc(Long movementId);
}
