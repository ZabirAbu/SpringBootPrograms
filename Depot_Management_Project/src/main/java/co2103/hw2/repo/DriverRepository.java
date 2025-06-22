package co2103.hw2.repo;

import co2103.hw2.model.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, Integer> {
    public Driver findByAge(int age);
}
