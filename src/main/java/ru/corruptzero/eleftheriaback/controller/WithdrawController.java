package ru.corruptzero.eleftheriaback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;
import ru.corruptzero.eleftheriaback.dto.WithdrawDTO;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;
import ru.corruptzero.eleftheriaback.mapper.WithdrawMapper;
import ru.corruptzero.eleftheriaback.service.WithdrawService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code WithdrawController} class is a RESTful web service controller for managing withdraws.
 * It maps HTTP requests to methods that perform CRUD operations on the {@link Withdraw} entity.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@RestController
@RequestMapping("/api/v2/withdraws")
@Slf4j
public class WithdrawController {
    /**
     * Mapper object for mapping between Withdraw and WithdrawDTO objects.
     */
    private final WithdrawMapper withdrawMapper = WithdrawMapper.INSTANCE;

    /**
     * Service object for handling withdraw-related logic.
     */
    @Autowired
    private WithdrawService withdrawService;

    /**
     * Handles a GET request to retrieve all withdraws from the database.
     *
     * @return a {@link ResponseEntity} containing a list of {@link WithdrawDTO} objects and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<WithdrawDTO>> getAllWithdraws() {
        List<Withdraw> withdraws = withdrawService.getAllWithdraws();
        List<WithdrawDTO> withdrawDTOs = withdraws.stream().map(withdrawMapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(withdrawDTOs, HttpStatus.OK);
    }

    /**
     * Handles a GET request to retrieve a withdraw by its id.
     *
     * @param id the id of the withdraw to retrieve
     * @return a {@link ResponseEntity} containing an {@link WithdrawDTO} object and an HTTP status code
     */
    @GetMapping("{id}")
    public ResponseEntity<WithdrawDTO> getWithdrawById(@PathVariable("id") Long id) {
        Optional<Withdraw> withdrawData = withdrawService.findById(id);
        return withdrawData.map(withdraw -> {
            WithdrawDTO withdrawDTO = withdrawMapper.toDTO(withdraw);
            return new ResponseEntity<>(withdrawDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Handles a POST request to create a new withdraw.
     *
     * @param withdrawDTO   the {@link WithdrawDTO} object representing the withdraw to create
     * @param bindingResult the result of the input validation
     * @return a {@link ResponseEntity} containing the created {@link WithdrawDTO} object and an HTTP status code
     * @throws InvalidEntityException if the input is invalid
     */
    @PostMapping
    public ResponseEntity<WithdrawDTO> createWithdraw(@Valid @RequestBody WithdrawDTO withdrawDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidEntityException("Invalid entity: " + bindingResult.getAllErrors());
        }
        Withdraw withdraw = withdrawMapper.toEntity(withdrawDTO);
        withdraw.setCreated_at(LocalDateTime.now());
        Withdraw createdWithdraw = withdrawService.save(withdraw);
        WithdrawDTO createdWithdrawDTO = withdrawMapper.toDTO(createdWithdraw);
        return new ResponseEntity<>(createdWithdrawDTO, HttpStatus.CREATED);
    }

    /**
     * Handles a PATCH request to update an existing withdraw.
     *
     * @param id          the id of the withdraw to update
     * @param withdrawDTO the updated withdraw data
     * @return a {@link ResponseEntity} containing a {@link Withdraw} object and an HTTP status code
     */
    @PatchMapping("{id}")
    public ResponseEntity<Withdraw> updateWithdraw(@PathVariable("id") Long id, @RequestBody WithdrawDTO withdrawDTO) {
        Optional<Withdraw> withdrawData = withdrawService.findById(id);
        if (withdrawData.isPresent()) {
            Withdraw withdraw = withdrawData.get();
            Withdraw updatedWithdraw = withdrawMapper.toEntity(withdrawDTO);
            if (updatedWithdraw.getStatus()!=null) {
                withdraw.setStatus(updatedWithdraw.getStatus());
            }
            if (updatedWithdraw.getAmount()!=null && updatedWithdraw.getAmount()>0) {
                withdraw.setAmount(updatedWithdraw.getAmount());
            }
            if (updatedWithdraw.getCurrency()!=null && !updatedWithdraw.getCurrency().isBlank()){
                withdraw.setCurrency(updatedWithdraw.getCurrency());
            }
            if (updatedWithdraw.getUser()!=null) {
                withdraw.setUser(updatedWithdraw.getUser());
            }
            return new ResponseEntity<>(withdrawService.save(withdraw), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles a DELETE request to delete a single withdraw by its id.
     *
     * @param id the id of the withdraw to delete
     * @return a {@link ResponseEntity} containing an HTTP status code
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteWithdraw(@PathVariable("id") Long id) {
        withdrawService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles a DELETE request to delete all withdraws.
     *
     * <p><strong>Note:</strong> This method is only intended for testing purposes.</p>
     *
     * @return a {@link ResponseEntity} containing an HTTP status code
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllWithdraws() {
        withdrawService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



