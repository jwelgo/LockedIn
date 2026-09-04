package com.backend.backend.Datatypes;

import java.util.Date;

public class Movement {
    private final String name;
    private final MovementType type;
    private final MuscleGroup muscleGroup;
    private final Date mostRecent;
    private final Progression progressionHistory;

    // Constructor
    public Movement(String name, MovementType type, MuscleGroup muscleGroup, Progression progressionHistory) {
        this.name = name;
        this.type = type;
        this.muscleGroup = muscleGroup;
        this.mostRecent = progressionHistory.getLatestDate();
        this.progressionHistory = progressionHistory;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public MovementType getType() {
        return this.type;
    }

    public MuscleGroup getMuscleGroup() {
        return this.muscleGroup;
    }

    public Date getMostRecentDate() {
        return this.mostRecent;
    }

    public Integer getMostRecentSets() {
        return this.progressionHistory.getSets(this.mostRecent);
    }

    public Integer getMostRecentReps() {
        return this.progressionHistory.getReps(this.mostRecent);
    }

    public Integer getMostRecentWeight() {
        return this.progressionHistory.getWeight(this.mostRecent);
    }

    // Modifiers
    public void addInstance(Date date, Integer sets, Integer reps, Integer weight) {
        this.progressionHistory.addPerformance(date, sets, reps, weight);
    }

    // Setters
    // DO NOT set the state of this class during execution
}
