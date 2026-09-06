package com.backend.backend.Repositories;

import com.backend.backend.Entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    Optional<Movement> findByName(String name);
}