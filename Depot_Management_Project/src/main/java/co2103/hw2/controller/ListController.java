package co2103.hw2.controller;

import co2103.hw2.Hw2Application;
import co2103.hw2.repo.DepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ListController {
    @Autowired
    private DepotRepository depotrepo;

    @RequestMapping("/list")
    public String listDepots(Model model) {
        model.addAttribute("depots", depotrepo.findAll());
        return "list";
    }
}
