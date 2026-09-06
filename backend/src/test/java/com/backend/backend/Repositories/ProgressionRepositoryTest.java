package com.backend.backend.Repositories;

import com.backend.backend.Entities.Progression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProgressionRepositoryTest {
    @Autowired
    private ProgressionRepository progressionRepository;

    private Progression progression;
    @Autowired
    private MachineRepository machineRepository;

    @BeforeEach
    void setUp() {
        progression = new Progression();
        progression.setDate(new Date());
        progression.setSets(2);
        progression.setReps(10);
        progression.setWeight(185);
    }

    // CREATE

    @Test
    void save_persistsProgressionWithGeneratedId() {
        Progression saved = progressionRepository.save(progression);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDate().toString().equals(new Date().toString()));
    }

    @Test
    void save_setsInitialVersionForOptimisticLocking() {
        Progression saved = progressionRepository.save(progression);

        assertThat(saved.getVersion()).isEqualTo(0L);
    }

    // READ

    @Test
    void findById_returnsProgressionWhenExists() {
        Progression saved = progressionRepository.save(progression);

        Optional<Progression> found = progressionRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertThat(found.get().getDate().toString().equals(new Date().toString()));
    }

    @Test
    void findById_returnsEmptyWhenProgressionDoesNotExist() {
        Optional<Progression> found = progressionRepository.findById(999L);

        assertThat(found).isEmpty();
    }

    @Test
    void findAll_returnsAllPersistedProgressions() {
        progressionRepository.save(progression);

        Progression second = new Progression();
        second.setDate(new Date(1700000000));
        second.setSets(3);
        second.setReps(12);
        second.setWeight(115);
        progressionRepository.save(second);

        List<Progression> all =  progressionRepository.findAll();

        assertThat(all).hasSize(2)
                .extracting(Progression::getDate)
                .containsExactlyInAnyOrder(new Date(1700000000), new Date());
    }

    @Test
    void findAll_pageable_respectsPageSize() {
        for (int i = 0; i < 5; i++) {
            Progression p = new Progression();
            p.setDate(new Date(1700000000 + i));
            p.setSets(1);
            p.setReps(1);
            p.setWeight(1);
            progressionRepository.save(p);
        }

        Pageable firstPageOfTwo = PageRequest.of(0,2);
        var page = progressionRepository.findAll(firstPageOfTwo);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(3);
    }

    @Test
    void findByDate_returnsMatchingProgression() {
        progressionRepository.save(progression);

        Optional<Progression> found = progressionRepository.findByDate(new Date());

        assertTrue(found.isPresent());
    }

    // UPDATE

    @Test
    void update_changesArePersistedOnSave() {
        Progression saved = progressionRepository.save(progression);

        saved.setReps(4);
        progressionRepository.save(saved);

        Progression reloaded = progressionRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloaded.getReps()).isEqualTo(4);
    }

    @Test
    void update_incrementsVersionOnChange() {
        Progression saved = progressionRepository.save(progression);
        Long originalVersion = saved.getVersion();

        saved.setSets(1);
        Progression updated = progressionRepository.saveAndFlush(saved);

        assertThat(updated.getVersion()).isGreaterThan(originalVersion);
    }

    @Test
    void update_staleVersionThrowsOptimisticLockingException () {
        Progression saved = progressionRepository.saveAndFlush(progression);

        // Simulate two concurrent editors loading the same row
        Progression editorA = progressionRepository.findById(saved.getId()).orElseThrow();
        Progression editorB = progressionRepository.findById(saved.getId()).orElseThrow();

        editorA.setWeight(10);
        progressionRepository.saveAndFlush(editorA);

        editorB.setWeight(20);
        assertThatThrownBy(() -> progressionRepository.saveAndFlush(editorB))
                .isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }

    // DELETE

    @Test
    void deleteById_removeProgression() {
        Progression saved = progressionRepository.save(progression);

        progressionRepository.deleteById(saved.getId());

        assertThat(progressionRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void existsById_falseAfterDelete() {
        Progression saved = progressionRepository.save(progression);
        progressionRepository.deleteById(saved.getId());

        assertThat(progressionRepository.existsById(saved.getId())).isFalse();
    }
}