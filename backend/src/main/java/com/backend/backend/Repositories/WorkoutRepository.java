package com.backend.backend.Repositories;

import com.backend.backend.Entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByDate(Date date);
}
