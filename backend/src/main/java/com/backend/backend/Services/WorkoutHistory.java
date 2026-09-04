package com.backend.backend.Services;

import com.backend.backend.Datatypes.Workout;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkoutHistory {
    public static List<Workout> getWorkoutHistory() {
        // get the entire workout History
        return null;
    }

    public static List<Workout> getWorkoutHistory(Date start) {
        // get the entire workout history dating bck to start
        return null;
    }

    public static List<Workout> getWorkoutHistory(Date start, Date end) {
        // get the entire workout history within passed range
        return null;
    }
}
