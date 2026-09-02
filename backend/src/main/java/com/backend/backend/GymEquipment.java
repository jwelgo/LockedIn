package com.backend.backend;

import java.util.HashMap;
import java.util.HashSet;

public class GymEquipment {
    private String name;
    private String brand;
    private final HashMap<String, String> settings;

    // Constructor
    public GymEquipment(String name, String brand) {
        this.name = name;
        this.brand = brand;
        this.settings = new HashMap<>();
    }

    // Getters
    public String getName(){
        return this.name;
    }

    public String getBrand(){
        return this.brand;
    }

    public HashSet<String> getSettings() {
        return new HashSet<>(this.settings.keySet());
    }

    public String getSettingValue(String setting) {
        return this.settings.get(setting);
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSetting(String settingKey, String settingValue) {
        this.settings.put(settingKey, settingValue);
    }
}
