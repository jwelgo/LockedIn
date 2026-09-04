package com.backend.backend.Repositories;

import com.backend.backend.Entities.Machine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MachineRepositoryTest {

    @Autowired
    private MachineRepository machineRepository;

    private Machine machine;

    @BeforeEach
    void setUp() {
        machine = new Machine();
        machine.setName("Leg Press");
        machine.setSettings("{\"seatPosition\":3,\"weightIncrement\":10}");
    }

    // CREATE

    @Test
    void save_persistsMachineWithGeneratedId() {
        Machine saved = machineRepository.save(machine);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Leg Press");
    }

    @Test
    void save_setsInitialVersionForOptimisticLocking() {
        Machine saved = machineRepository.save(machine);

        assertThat(saved.getVersion()).isEqualTo(0L);
    }

    // READ

    @Test
    void findById_returnsMachineWhenExists() {
        Machine saved = machineRepository.save(machine);

        Optional<Machine> found = machineRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertThat(found.get().getName()).isEqualTo("Leg Press");
    }

    @Test
    void findById_returnsEmptyWhenMachineDoesNotExist() {
        Optional<Machine> found = machineRepository.findById(999L);

        assertThat(found).isEmpty();
    }

    @Test
    void findAll_returnsAllPersistedMachines() {
        machineRepository.save(machine);

        Machine second = new Machine();
        second.setName("Cable Row");
        second.setSettings("{\"seatPosition\":1}");
        machineRepository.save(second);

        List<Machine> all = machineRepository.findAll();

        assertThat(all).hasSize(2)
                .extracting(Machine::getName)
                .containsExactlyInAnyOrder("Leg Press", "Cable Row");
    }

    @Test
    void findAll_pageable_respectsPageSize() {
        for (int i = 0; i < 5; i++) {
            Machine m = new Machine();
            m.setName("Machine " + i);
            m.setSettings("{}");
            machineRepository.save(m);
        }

        Pageable firstPageOfTwo = PageRequest.of(0, 2);
        var page = machineRepository.findAll(firstPageOfTwo);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(3);
    }

    @Test
    void findByName_returnsMatchingMachine() {
        machineRepository.save(machine);

        Optional<Machine> found = machineRepository.findByName("Leg Press");

        assertTrue(found.isPresent());
    }

    // UPDATE

    @Test
    void update_changesArePersistedOnSave() {
        Machine saved = machineRepository.save(machine);

        saved.setName("Leg Press Pro");
        machineRepository.save(saved);

        Machine reloaded = machineRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloaded.getName()).isEqualTo("Leg Press Pro");
    }

    @Test
    void update_incrementsVersionOnChange() {
        Machine saved = machineRepository.save(machine);
        Long originalVersion = saved.getVersion();

        saved.setName("Leg Press Pro");
        Machine updated = machineRepository.saveAndFlush(saved);

        assertThat(updated.getVersion()).isGreaterThan(originalVersion);
    }

    @Test
    void update_staleVersionThrowsOptimisticLockingException() {
        Machine saved = machineRepository.saveAndFlush(machine);

        // Simulate two concurrent editors loading the same row
        Machine editorA = machineRepository.findById(saved.getId()).orElseThrow();
        Machine editorB = machineRepository.findById(saved.getId()).orElseThrow();

        editorA.setName("Edited by A");
        machineRepository.saveAndFlush(editorA);

        editorB.setName("Edited by B");
        assertThatThrownBy(() -> machineRepository.saveAndFlush(editorB))
                .isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }

    // DELETE

    @Test
    void deleteById_removesMachine() {
        Machine saved = machineRepository.save(machine);

        machineRepository.deleteById(saved.getId());

        assertThat(machineRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void existsById_falseAfterDelete() {
        Machine saved = machineRepository.save(machine);
        machineRepository.deleteById(saved.getId());

        assertThat(machineRepository.existsById(saved.getId())).isFalse();
    }
}