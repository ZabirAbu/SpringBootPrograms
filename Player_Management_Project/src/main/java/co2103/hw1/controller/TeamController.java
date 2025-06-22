package co2103.hw1.controller;



import co2103.hw1.Hw1Application;
import co2103.hw1.domain.Team;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class TeamController {
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators( new TeamValidator());
    }
    @RequestMapping("/teams")
    public String listTeams(Model model) {
        model.addAttribute("teams", Hw1Application.teams);
        return "teams/list";
    }


    @RequestMapping("/newTeam")
    public String newTeam(Model model) {
        model.addAttribute("team", new Team());
        return "teams/form";
    }

    @PostMapping("/addTeam")
    public String addTeam(@Valid @ModelAttribute Team team, BindingResult result) {
        if (result.hasErrors()) {
            return "teams/form";
        }
        Hw1Application.teams.add(team);
        return "redirect:/teams";
    }
}
