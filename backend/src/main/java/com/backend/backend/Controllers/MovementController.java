package com.backend.backend.Controllers;

import com.backend.backend.DTOs.MovementRequest;
import com.backend.backend.DTOs.MovementResponse;
import com.backend.backend.Services.MovementService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movements")
public class MovementController {
    private final MovementService service;

    public MovementController(MovementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MovementResponse> create(@Valid @RequestBody MovementRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @GetMapping("/{id}")
    public MovementResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<MovementResponse> list (Pageable pageable) {
        return service.list(pageable);
    }

    @PutMapping("/{id}")
    public MovementResponse update(@PathVariable Long id, @Valid @RequestBody MovementRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
