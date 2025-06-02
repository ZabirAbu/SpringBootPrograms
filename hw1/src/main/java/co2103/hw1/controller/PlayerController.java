package co2103.hw1.controller;

import co2103.hw1.Hw1Application;
import co2103.hw1.domain.Player;
import co2103.hw1.domain.Team;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PlayerController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators( new PlayerValidator());
    }

    @RequestMapping("/players")
    public String locLeague(@RequestParam("team")int teamId, Model model, @ModelAttribute Player player) {
        for (Team a : Hw1Application.teams) {
            if (a.getId() == teamId) {
                model.addAttribute("players",a.getPlayers());
                model.addAttribute("team",teamId);
            }
        }
        return "players/list";
}
    @RequestMapping("/newPlayer")
    public String newPlayer(@Valid Model model, @ModelAttribute Player player, @RequestParam(name = "team") int teamId) {
        model.addAttribute("team", teamId);
        model.addAttribute("player", new Player());
        return "players/form";
    }

    @PostMapping("/addPlayer")
    public String addPlayer(@Valid @ModelAttribute Player player, BindingResult result, @RequestParam(name = "team")  int teamId, Model model) {
        model.addAttribute("team", teamId);
        if (result.hasErrors()) {
            return "players/form";
        }

        Team addingTo = null;

        for (Team x: Hw1Application.teams) {
            if (x.getId() == teamId) {
                addingTo = x;
            }
        }
        addingTo.getPlayers().add(player);

        return "redirect:/teams";
    }
}
