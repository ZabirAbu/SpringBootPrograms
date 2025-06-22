package co2103.hw2.repo;

import co2103.hw2.model.Bus;
import co2103.hw2.model.Depot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepotRepository
extends CrudRepository<Depot, Integer> {
    public Depot findByAddress(String address);
    public List<Bus> findByBusesContains(String city);

}
