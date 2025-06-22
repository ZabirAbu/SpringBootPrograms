package co2103.hw2;

import co2103.hw2.model.Bus;
import co2103.hw2.model.Depot;
import co2103.hw2.model.Driver;
import co2103.hw2.repo.BusRepository;
import co2103.hw2.repo.DepotRepository;
import co2103.hw2.repo.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class Hw2Application implements ApplicationRunner {


    @Autowired
    private DepotRepository depotrepo;
    @Autowired
    private BusRepository brepo;
    @Autowired
    private DriverRepository drivrepo;

    public static void main(String[] args) {
        SpringApplication.run(Hw2Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Depot dep1 = new Depot();

        dep1.setAddress("Central Bus Square");
        dep1 = depotrepo.save(dep1);
        dep1.setBuses(new ArrayList<>());

        Bus bus1 = new Bus();
        bus1.setCity("Manchester");
        dep1 = depotrepo.save(dep1);


        dep1.setOldestBus(bus1);

        bus1 = brepo.save(bus1);

        bus1.setDepots(new ArrayList<>());

        Driver d1 = new Driver();
        d1.setAge(29);
        d1 = drivrepo.save(d1);

        bus1.setCEO(d1);
        bus1.getDrivers().add(d1);

        dep1.getBuses().add(bus1);
        dep1 = depotrepo.save(dep1);
    }
}
