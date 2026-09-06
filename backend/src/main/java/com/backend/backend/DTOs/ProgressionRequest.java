package com.backend.backend.DTOs;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record ProgressionRequest(@NotBlank Date date, Integer sets, Integer reps, Integer weight) {}
