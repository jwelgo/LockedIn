package com.backend.backend.Services;

import com.backend.backend.DTOs.MovementRequest;
import com.backend.backend.DTOs.MovementResponse;
import com.backend.backend.Entities.Movement;
import com.backend.backend.Exceptions.ResourceNotFoundException;
import com.backend.backend.Repositories.MovementRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovementService {
    private final MovementRepository repo;

    public MovementService(MovementRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public MovementResponse create(MovementRequest req) {
        Movement m = new Movement();
        m.setName(req.name());
        m.setMuscleGroup(req.muscleGroup());
        m.setMachine(req.machine());
        m.setProgression(req.progression());
        return toResponse(repo.save(m));
    }

    public MovementResponse getById(Long id) {
        return repo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Movement " + id + " not found"));
    }

    public Page<MovementResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public MovementResponse update(Long id, MovementRequest req) {
        Movement m = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movement " + id + " not found"));
        m.setName(req.name());
        m.setMuscleGroup(req.muscleGroup());
        return toResponse(m);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Movement " + id + " not found");
        repo.deleteById(id);
    }

    private MovementResponse toResponse(Movement m) {
        return new MovementResponse(m.getId(), m.getName(), m.getMuscleGroup(), m.getMachine(), m.getProgression());
    }
}
