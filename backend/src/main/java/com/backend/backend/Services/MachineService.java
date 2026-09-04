package com.backend.backend.Services;

import com.backend.backend.DTOs.MachineRequest;
import com.backend.backend.DTOs.MachineResponse;
import com.backend.backend.Entities.Machine;
import com.backend.backend.Exceptions.ResourceNotFoundException;
import com.backend.backend.Repositories.MachineRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class MachineService {
    private final MachineRepository repo;

    public MachineService(MachineRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public MachineResponse create(MachineRequest req) {
        Machine m = new Machine();
        m.setName(req.name());
        m.setSettings(req.settings());
        return toResponse(repo.save(m));
    }

    public MachineResponse getById(Long id) {
        return repo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Machine " + id + " not found"));
    }

    public Page<MachineResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public MachineResponse update(Long id, MachineRequest req) {
        Machine m = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine " + id + " not found"));
        m.setName(req.name());
        m.setSettings(req.settings());
        return toResponse(m); // dirty checking flushes on commit, no explicit save() needed
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Machine " + id + " not found");
        repo.deleteById(id);
    }

    private MachineResponse toResponse(Machine m) {
        return new MachineResponse(m.getId(), m.getName(), m.getSettings());
    }
}
