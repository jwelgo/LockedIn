package com.backend.backend.Controllers;

import com.backend.backend.DTOs.MachineRequest;
import com.backend.backend.DTOs.MachineResponse;
import com.backend.backend.Services.MachineService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/machines")
public class MachineController {
    private final MachineService service;

    public MachineController(MachineService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MachineResponse> create(@Valid @RequestBody MachineRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping("/{id}")
    public MachineResponse get(@PathVariable Long id) {return service.getById(id);}

    @GetMapping
    public Page<MachineResponse> list(Pageable pageable) { return service.list(pageable); }

    @PutMapping("/{id}")
    public MachineResponse update(@PathVariable Long id, @Valid @RequestBody MachineRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
