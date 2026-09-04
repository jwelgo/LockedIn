package com.backend.backend.Controllers;

import com.backend.backend.Services.WorkoutHistory;
import com.backend.backend.Datatypes.Workout;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/workout-history")

public class WorkoutController {
    @GetMapping
    public List<Workout> getWorkoutHistory() {
        // return all workouts
        return WorkoutHistory.getWorkoutHistory();
    }

    @GetMapping("/{start}")
    public List<Workout> getWorkoutHistoryFromDate(@PathVariable Date start) {
        // return all workouts from date provided onwards
        return WorkoutHistory.getWorkoutHistory(start);
    }

    @GetMapping("/{start}&{end}")
    public List<Workout> getWorkoutHistoryFromDateRange(@PathVariable Date start, @PathVariable Date end) {
        // return all workouts withing provided date range
        return WorkoutHistory.getWorkoutHistory(start, end);
    }

    @PostMapping
    public ResponseEntity<Workout> postNewWorkout() {
        // log a new workout in the database
        return null;
    }
}
