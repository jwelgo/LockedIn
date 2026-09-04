package com.backend.backend.Datatypes;

import java.util.ArrayList;
import java.util.Date;

public class Workout {
    private Date dateLogged;
    private WorkoutType type;
    private final ArrayList<Movement> movements;
    private String notes;

    public Workout(Date date, WorkoutType type, String notes) {
        this.dateLogged = date;
        this.type = type;
        this.movements = new ArrayList<>();
        this.notes = notes;
    }

    // Getters
    public Date getDateLogged() {
        return this.dateLogged;
    }

    public WorkoutType getWorkoutType() {
        return this.type;
    }

    public ArrayList<Movement> getMovements() {
        return this.movements;
    }

    public String getNotes() {
        return this.notes;
    }

    // Modifiers
    public ArrayList<Movement> addMovement(Movement newMovement) {
        this.movements.add(newMovement);
        return this.movements;
    }

    public ArrayList<Movement> removeMovement(Movement movement) {
        this.movements.remove(movement);
        return this.movements;
    }

    public String addNote(String addition) {
        this.notes = String.join(System.lineSeparator(),this.notes,addition);
        return this.notes;
    }

    // Setters
    public void setDate(Date date) {
        this.dateLogged = date;
    }

    public void setType(WorkoutType type) {
        this.type = type;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
