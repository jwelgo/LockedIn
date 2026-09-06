package com.backend.backend.Services;

import com.backend.backend.DTOs.ProgressionRequest;
import com.backend.backend.DTOs.ProgressionResponse;
import com.backend.backend.Entities.Progression;
import com.backend.backend.Exceptions.ResourceNotFoundException;
import com.backend.backend.Repositories.ProgressionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProgressionService {
    private final ProgressionRepository repo;

    public ProgressionService(ProgressionRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ProgressionResponse create(ProgressionRequest req) {
        Progression p = new Progression();
        p.setDate(req.date());
        p.setSets(req.sets());
        p.setReps(req.reps());
        p.setWeight(req.weight());
        return toResponse(repo.save(p));
    }

    public ProgressionResponse getById(Long id) {
        return repo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Progression " + id + " not found"));
    }

    public Page<ProgressionResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public ProgressionResponse update(Long id, ProgressionRequest req) {
        Progression p = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progression " + id + " not found"));
        p.setDate(req.date());
        p.setSets(req.sets());
        p.setReps(req.reps());
        p.setWeight(req.weight());
        return toResponse(p);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Progression " + id + " not found");
        repo.deleteById(id);
    }

    private ProgressionResponse toResponse(Progression p) {
        return new ProgressionResponse(p.getId(), p.getDate(), p.getSets(), p.getReps(), p.getWeight());
    }
}
