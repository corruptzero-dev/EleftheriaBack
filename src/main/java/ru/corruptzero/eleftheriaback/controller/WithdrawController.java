package ru.corruptzero.eleftheriaback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.corruptzero.eleftheriaback.domain.entity.EWithdrawStatus;
import ru.corruptzero.eleftheriaback.domain.entity.Withdraw;
import ru.corruptzero.eleftheriaback.service.impl.WithdrawServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/withdraws")
@Slf4j
public class WithdrawController {

    @Autowired
    private WithdrawServiceImpl withdrawServiceImpl;

    @GetMapping
    public ResponseEntity<List<Withdraw>> getAllWithdraws() {
        try {
            List<Withdraw> withdraws = withdrawServiceImpl.getAllWithdraws();
            if (withdraws.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(withdraws, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting all withdraws: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/pending")
    public ResponseEntity<List<Withdraw>> getPendingWithdraws() {
        try {
            List<Withdraw> withdraws = withdrawServiceImpl.findPendingWithdraws();
            if (withdraws.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(withdraws, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting pending withdraw: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Withdraw> getWithdrawById(@PathVariable("id") Long id) {
        try {
        Optional<Withdraw> withdrawData = withdrawServiceImpl.findById(id);
        return withdrawData.map(withdraw -> new ResponseEntity<>(withdraw, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error getting withdraw: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Withdraw> createWithdraw(@RequestParam EWithdrawStatus status,
                                                   @RequestParam Integer amount) {
        try {
            Withdraw withdraw = new Withdraw();
            withdraw.setStatus(status);
            withdraw.setAmount(amount);
            Withdraw _withdraw = withdrawServiceImpl.save(withdraw);
            return new ResponseEntity<>(_withdraw, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating withdraw: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Withdraw> updateWithdraw(@PathVariable("id") Long id,
                                                   @RequestParam(required = false) EWithdrawStatus status,
                                                   @RequestParam(required = false) Integer amount) {
        try {
            Optional<Withdraw> withdrawData = withdrawServiceImpl.findById(id);
            if (withdrawData.isPresent()) {
                Withdraw _withdraw = withdrawData.get();
                if (status != null) _withdraw.setStatus(status);
                if (amount != null) _withdraw.setAmount(amount);
                return new ResponseEntity<>(withdrawServiceImpl.save(_withdraw), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error updating withdraw: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteWithdraw(@PathVariable("id") Long id) {
        try {
            withdrawServiceImpl.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting withdraw: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllWithdraws() {
        try {
            withdrawServiceImpl.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting all withdraws: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
