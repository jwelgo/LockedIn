package com.backend.backend;

public class GymMachine extends GymEquipment {
    private GymMachineType type;

    public GymMachine(String name, String brand, GymMachineType type) {
        super(name, brand);
        this.type = type;
    }

    // Getters
    public GymMachineType getType() {
        return this.type;
    }

    // Setters
    public void setType(GymMachineType type) {
        this.type = type;
    }
}
