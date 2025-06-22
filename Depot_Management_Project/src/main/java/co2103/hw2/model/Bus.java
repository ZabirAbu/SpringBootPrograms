package co2103.hw2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Bus {
    @Id
    private String city;
    @ManyToMany(mappedBy = "buses")
    private List<Depot> depots;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List <Driver> drivers = new ArrayList<>();
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Driver CEO;

    public String getCity() {
        return city;
    }

    public List<Depot> getDepots() {
        return depots;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public Driver getCEO() {
        return CEO;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDepots(List<Depot> depots) {
        this.depots = depots;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void setCEO(Driver CEO) {
        this.CEO = CEO;
    }

    @Override
    public String toString() {
        return "Bus{" + "city='" + city + '\'' +  ", drivers=" + drivers + ", CEO=" + CEO + '}';
    }
}
