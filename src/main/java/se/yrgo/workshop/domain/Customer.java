package se.yrgo.workshop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String phoneNumber;
    @OneToMany
    List<Vehicle> vehicles = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }
}
