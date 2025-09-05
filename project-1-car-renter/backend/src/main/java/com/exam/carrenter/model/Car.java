package com.exam.carrenter.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "licenseplate",nullable=false)
    private String licensePlate;
    @Column(nullable=false)
    private String type;
    @Column (name = "numberofpassangers")
    private Integer numberOfPassangers;
    @Column(name = "fueltype",nullable=false)
    private String fuelType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumberOfPassangers() {
        return numberOfPassangers;
    }

    public void setNumberOfPassangers(Integer numberOfPassangers) {
        this.numberOfPassangers = numberOfPassangers;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
