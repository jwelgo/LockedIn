package com.backend.backend.DTOs;

import java.util.Date;

public record ProgressionResponse(Long id, Date date, Integer sets, Integer reps, Integer weight) {}
