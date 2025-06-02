package co2103.hw2.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Depot {
    @Id
    @GeneratedValue
    private int id;
    private String address;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Bus> buses;
    @OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Bus oldestBus;

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public Bus getOldestBus() {
        return oldestBus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public void setOldestBus(Bus oldestBus) {
        this.oldestBus = oldestBus;
    }

    @Override
    public String toString() {
        return "Depot{" + "id=" + id + ", address='" + address + '\'' + ", buses=" + buses + ", oldestBus=" + oldestBus + '}';
    }
}

//ID: ${dep.id}, Address: ${dep.toString()}