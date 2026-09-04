package com.backend.backend.Datatypes;

public class GymCable extends GymEquipment {
    private GymCableType handleType;
    private GymCableType weightType;

    public GymCable(String name, String brand, GymCableType handleType, GymCableType weightType) {
        super(name, brand);
        this.handleType = handleType;
        this.weightType = weightType;
    }

    // Getters
    public GymCableType getHandleType() {
        return this.handleType;
    }

    public GymCableType getWeightType() {
        return this.weightType;
    }

    // Setters
    public void setHandleType(GymCableType handleType) {
        this.handleType = handleType;
    }

    public void setWeightType(GymCableType weightType) {
        this.weightType = weightType;
    }
}
