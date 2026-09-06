package com.backend.backend.Controllers;

import com.backend.backend.DTOs.ProgressionRequest;
import com.backend.backend.DTOs.ProgressionResponse;
import com.backend.backend.Services.ProgressionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progressions")
public class ProgressionController {
    private final ProgressionService service;

    public ProgressionController(ProgressionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProgressionResponse> create(@Valid @RequestBody ProgressionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping("/{id}")
    public ProgressionResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<ProgressionResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @PutMapping("/{id}")
    public ProgressionResponse update(@PathVariable Long id, @Valid @RequestBody ProgressionRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
