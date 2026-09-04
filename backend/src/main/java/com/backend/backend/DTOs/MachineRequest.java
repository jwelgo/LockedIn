package com.backend.backend.DTOs;

import jakarta.validation.constraints.NotBlank;

public record MachineRequest(@NotBlank String name, String settings) {}
