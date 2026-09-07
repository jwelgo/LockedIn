package com.backend.backend.DTOs;

import com.backend.backend.Datatypes.WorkoutType;
import com.backend.backend.Entities.WorkoutMovement;

import java.util.Date;
import java.util.List;

public record WorkoutResponse(Long id, Date date, List<WorkoutMovement> workoutMovements, WorkoutType type, String notes) {}
