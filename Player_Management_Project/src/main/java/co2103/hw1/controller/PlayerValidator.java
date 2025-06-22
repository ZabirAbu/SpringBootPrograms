package co2103.hw1.controller;

import co2103.hw1.Hw1Application;
import co2103.hw1.domain.Player;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.lang.reflect.Array;
import java.util.Arrays;

public class PlayerValidator implements Validator{

        @Override
        public boolean supports(Class<?> clazz) {return Player.class.equals(clazz) ;}
        @Override
        public void validate(Object target, Errors errors) {
            Player player = (Player) target;
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "", "No name provided");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "", "No description provided");


            if(!Arrays.asList(Hw1Application.positions).contains(player.getPosition())){
                errors.rejectValue("position", "", "Wrong position");
            }


            if (player.getAge() < 18 || player.getAge() > 100) {
                errors.rejectValue("age", "", "Age out of bounds");
            }

        }

}




