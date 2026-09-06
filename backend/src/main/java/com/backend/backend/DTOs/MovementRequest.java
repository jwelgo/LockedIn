package com.backend.backend.DTOs;

import com.backend.backend.Datatypes.MuscleGroup;
import com.backend.backend.Entities.Machine;
import com.backend.backend.Entities.Progression;
import jakarta.validation.constraints.NotBlank;

public record MovementRequest(@NotBlank String name, MuscleGroup muscleGroup, Machine machine, Progression progression) {}
