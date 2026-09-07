package com.backend.backend.DTOs;

import com.backend.backend.Datatypes.WorkoutType;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

public record WorkoutRequest(@NotBlank Date date, List<WorkoutMovementRequest> movements, WorkoutType type, String notes) {}
