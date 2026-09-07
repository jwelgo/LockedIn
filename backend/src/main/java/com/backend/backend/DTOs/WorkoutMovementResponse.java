package com.backend.backend.DTOs;

public record WorkoutMovementResponse(Long id, Long movementId, String movementName, Integer sortOrder, Integer sets, Integer reps, Double weight) {}
