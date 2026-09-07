package com.backend.backend.DTOs;

import jakarta.validation.constraints.NotBlank;

public record WorkoutMovementRequest(@NotBlank Long movementId, Integer sortOrder, Integer sets, Integer reps, Double weight) {}
