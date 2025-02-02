package com.nighthawk.spring_portfolio.mvc.mines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/casino/mines")
public class MinesApiController {
    
    @Autowired
    private PersonJpaRepository personJpaRepository;
    private MinesBoard board;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MinesRequest {
        private double betSize;
        private String uid;
    }

    @GetMapping("/{xCoord}/{yCoord}")
    public ResponseEntity<Boolean> getMine(@PathVariable int xCoord, @PathVariable int yCoord) {
        log.info("Checking mine at coordinates ({}, {})", xCoord, yCoord);
        return new ResponseEntity<>(board.checkMine(xCoord, yCoord), HttpStatus.OK);
    }

    @PostMapping("/winnings")
    public ResponseEntity<Double> calculateWinnings(@RequestBody MinesRequest minesRequest) {
        Person user = personJpaRepository.findByUid(minesRequest.getUid());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double betSize = minesRequest.getBetSize();
        double winnings = board.winnings() * betSize;
        double updatedBalance = user.getBalanceDouble() + winnings;
        user.setBalanceString(updatedBalance);
        personJpaRepository.save(user);
        
        return new ResponseEntity<>(user.getBalanceDouble(), HttpStatus.OK);
    }

    @PostMapping("/stakes/{stakes}")
    public ResponseEntity<Double> postStakes(@PathVariable String stakes, @RequestBody MinesRequest minesRequest) {
        Person user = personJpaRepository.findByUid(minesRequest.getUid());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        double betSize = minesRequest.getBetSize();
        double updatedBalance = user.getBalanceDouble() - betSize;
        if (updatedBalance < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setBalanceString(updatedBalance);
        personJpaRepository.save(user);
        board = new MinesBoard(stakes);
        
        return new ResponseEntity<>(user.getBalanceDouble(), HttpStatus.OK);
    }

    @GetMapping("/balance/{uid}")
    public ResponseEntity<Double> getBalance(@PathVariable String uid) {
        Person user = personJpaRepository.findByUid(uid);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.getBalanceDouble(), HttpStatus.OK);
    }
}