package com.backend.backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "machines")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String settings;

    @Version
    private Long version;

    public Long getId() {return this.id;}
    public String getName() {return this.name;}
    public String getSettings() {return this.settings;}
    public Long getVersion() {return this.version;}

    public void setName(String name) {this.name = name;}
    public void setSettings(String settings) {this.settings = settings;}
    public void setVersion(Long version) {this.version = version;}
}
