package co2103.hw2.repo;

import co2103.hw2.model.Bus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BusRepository extends CrudRepository<Bus, String> {
    public Bus findByCity(String city);
}
