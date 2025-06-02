package co2103.hw2.controller;

import co2103.hw2.model.Bus;
import co2103.hw2.model.Depot;
import co2103.hw2.model.Driver;
import co2103.hw2.repo.BusRepository;
import co2103.hw2.repo.DepotRepository;
import co2103.hw2.repo.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class DeleteController {
    @Autowired
    private DepotRepository depotrepo;

    @Autowired
    private BusRepository brepo;

    @Autowired
    private DriverRepository drivrepo;


    @GetMapping("/deleteDepot")
    public String delDepot(@RequestParam int id) {
        Optional<Depot> d = depotrepo.findById(id);
        if (d.isPresent()) {
            System.out.println(d.toString());
            depotrepo.deleteById(id);
        }
        return "redirect:/list";
    }


    @GetMapping("/deleteBus")
    public String delBus(@RequestParam String city, Model m) {
        for (Bus b : brepo.findAll()) {
            if (b.getCity().equals(city)) {
                for(Depot dep : depotrepo.findAll()) {
                    dep.getBuses().remove(b);
                    if (dep.getOldestBus().getCity().equals(city)) {
                        dep.setOldestBus(null);
                    }
                    depotrepo.save(dep);
                }
            }
        }
        return "redirect:/list";
    }

    @GetMapping("/deleteDriver")
    public String delDriver(@RequestParam Integer id, Model m) {
        for (Driver driver : drivrepo.findAll()) {
            if (driver.getId()==(id)) {
                for(Bus b: brepo.findAll()) {
                    b.getDrivers().remove(driver);
                    if (b.getCEO().getId()==(id)) {
                        b.setCEO(null);
                    }
                }
                drivrepo.save(driver);
            }

        }
        return "redirect:/list";
    }
}


