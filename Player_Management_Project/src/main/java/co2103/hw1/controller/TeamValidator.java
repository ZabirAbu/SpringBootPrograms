package co2103.hw1.controller;


import co2103.hw1.Hw1Application;
import co2103.hw1.domain.Team;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import org.springframework.validation.Validator;

public class TeamValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {return Team.class.equals(clazz) ;}
    @Override
    public void validate(Object target, Errors errors) {
        Team team = (Team) target;
        for (Team i : Hw1Application.teams) {
            if (i.getId() == team.getId()) {
                errors.rejectValue("id", "", "ID already being used");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "", "No location provided");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "league", "", "No league provided");
    }

}
