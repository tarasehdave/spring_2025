package com.nighthawk.spring_portfolio.mvc.dice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;

import lombok.Getter;

@RestController
@RequestMapping("/api/casino/dice")
public class DiceApiController {

    @Autowired
    private PersonJpaRepository personJpaRepository;
    

    @Getter 
    public static class DiceRequest {
        private double winChance;
        private double betSize;
        private String email;
    }


    @PostMapping("/calculate")
    public ResponseEntity<Double> postDice(@RequestBody DiceRequest diceRequest) {
        System.out.println("Received request: " + diceRequest);
        Dice dice = new Dice(diceRequest.getWinChance(), diceRequest.getBetSize());
        System.out.println(diceRequest.getEmail());
        
        Person user = personJpaRepository.findByEmail(diceRequest.getEmail());

        double currentBalance = user.getBalanceDouble();
        System.out.println(user.getBalanceDouble());
        double updatedBalance = currentBalance - diceRequest.getBetSize();
        user.setBalanceString(updatedBalance);
        personJpaRepository.save(user);  // Save the updated balance

        return new ResponseEntity<>(user.getBalanceDouble(), HttpStatus.OK);  // Return updated user data
    }

}
