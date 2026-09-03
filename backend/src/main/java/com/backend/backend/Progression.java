package com.backend.backend;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Progression {
    private HashMap<Date, Integer> sets;
    private HashMap<Date, Integer> reps;
    private HashMap<Date, Integer> weight;

    /** ****************************************************************
     *      DO NOT use/implement a constructor for this Object.
     *      This Object should be loaded in from a SQL table and
     *      a separate library will handle populating private data
     *      fields.
     *  ****************************************************************/

    // Getters
    public HashSet<Date> getDates() {
        return new HashSet<>(sets.keySet());
    }

    public Date getLatestDate() {
        HashSet<Date> dates = getDates();
        Date latest = new Date(Long.MIN_VALUE); // Earlier than any valid workout date
        for (Date currentDate: dates) {
            if (latest.before(currentDate)) {
                latest = currentDate;
            }
        }
        return latest;
    }

    public Integer getSets(Date date) {
        return this.sets.get(date);
    }

    public Integer getReps(Date date) {
        return this.reps.get(date);
    }

    public Integer getWeight(Date date) {
        return this.weight.get(date);
    }

    // Modifiers
    public void addWorkout(Date date, Integer sets, Integer reps, Integer weight) {
        this.sets.put(date, sets);
        this.reps.put(date, reps);
        this.weight.put(date, weight);
    }

    // Setters
    public void setSets(HashMap<Date, Integer> setsFromDB) {
        this.sets = setsFromDB;
    }

    public void setReps(HashMap<Date, Integer> repsFromDB) {
        this.reps = repsFromDB;
    }

    public void setWeight(HashMap<Date, Integer> weightsFromDB) {
        this.weight = weightsFromDB;
    }
}
