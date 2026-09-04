package com.backend.backend.Repositories;

import com.backend.backend.Entities.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    Optional<Machine> findByName(String name);
}
