package com.backend.backend.Repositories;

import com.backend.backend.Entities.Progression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ProgressionRepository extends JpaRepository<Progression, Long> {
    Optional<Progression> findByDate(Date date);
}
