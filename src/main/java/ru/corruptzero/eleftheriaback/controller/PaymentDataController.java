package ru.corruptzero.eleftheriaback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.dto.PaymentDataDTO;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;
import ru.corruptzero.eleftheriaback.mapper.PaymentDataMapper;
import ru.corruptzero.eleftheriaback.service.PaymentDataService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code PaymentDataController} class is a RESTful web service controller for managing payment data.
 * It maps HTTP requests to methods that perform CRUD operations on the {@link PaymentData} entity.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@RestController
@RequestMapping("/api/v2/payment_data")
@Slf4j
public class PaymentDataController {

    private final PaymentDataMapper paymentDataMapper = PaymentDataMapper.INSTANCE;
    @Autowired
    private PaymentDataService paymentDataService;

    /**
     * Handles a GET request to retrieve all payment data from the database.
     *
     * @return a {@link ResponseEntity} containing a list of {@link PaymentDataDTO} objects and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<PaymentDataDTO>> getAllPaymentData() {
        List<PaymentData> paymentData = paymentDataService.getAllPaymentData();
        List<PaymentDataDTO> paymentDataDTOs = paymentData.stream().map(paymentDataMapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(paymentDataDTOs, HttpStatus.OK);
    }

    /**
     * Handles a GET request to retrieve a payment data by its id.
     *
     * @param id the id of the payment data to retrieve
     * @return a {@link ResponseEntity} containing an {@link PaymentDataDTO} object and an HTTP status code
     */
    @GetMapping("{id}")
    public ResponseEntity<PaymentDataDTO> getPaymentDataById(@PathVariable("id") Long id) {
        Optional<PaymentData> paymentDataData = paymentDataService.findById(id);
        return paymentDataData.map(paymentData -> {
            PaymentDataDTO paymentDataDTO = paymentDataMapper.toDTO(paymentData);
            return new ResponseEntity<>(paymentDataDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Handles a POST request to create a new payment data.
     *
     * @param paymentDataDTO the {@link PaymentDataDTO} object representing the payment data to create
     * @param bindingResult  the result of the input validation
     * @return a {@link ResponseEntity} containing the created {@link PaymentDataDTO} object and an HTTP status code
     * @throws InvalidEntityException if the input is invalid
     */
    @PostMapping
    public ResponseEntity<PaymentDataDTO> createPaymentData(@Valid @RequestBody PaymentDataDTO paymentDataDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidEntityException("Invalid payment data: " + bindingResult.getAllErrors());
        }
        PaymentData paymentData = paymentDataMapper.toEntity(paymentDataDTO);
        PaymentData createdPaymentData = paymentDataService.save(paymentData);
        PaymentDataDTO createdPaymentDataDTO = paymentDataMapper.toDTO(createdPaymentData);
        return new ResponseEntity<>(createdPaymentDataDTO, HttpStatus.CREATED);
    }

    /**
     * Handles a PATCH request to update an existing payment data.
     *
     * @param id             the id of the payment data to update
     * @param paymentDataDTO the updated payment data
     * @return a {@link ResponseEntity} containing an {@link PaymentDataDTO} object and an HTTP status code
     */
    @PatchMapping("{id}")
    public ResponseEntity<PaymentData> updatePaymentData(@PathVariable("id") Long id, @RequestBody PaymentDataDTO paymentDataDTO) {
        Optional<PaymentData> paymentDataOptional = paymentDataService.findById(id);
        if (paymentDataOptional.isPresent()) {
            PaymentData paymentData = paymentDataOptional.get();
            PaymentData updatedPaymentData = paymentDataMapper.toEntity(paymentDataDTO);
            if (updatedPaymentData.getAccountNumber()!=null && !updatedPaymentData.getAccountNumber().isBlank()){
                paymentData.setPaymentMethod(updatedPaymentData.getPaymentMethod());
            }
            if (updatedPaymentData.getAccountNumber()!=null && !updatedPaymentData.getAccountNumber().isBlank()){
                paymentData.setAccountNumber(updatedPaymentData.getAccountNumber());
            }
            if (updatedPaymentData.getBankName()!=null && updatedPaymentData.getBankName().isBlank()){
                paymentData.setBankName(updatedPaymentData.getBankName());
            }
            if (updatedPaymentData.getUser()!=null){
                paymentData.setUser(updatedPaymentData.getUser());
            }
            PaymentData savedPaymentData = paymentDataService.save(paymentData);
            return new ResponseEntity<>(savedPaymentData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles a DELETE request to delete a single payment data by its id.
     *
     * @param id the id of the payment data to delete
     * @return a {@link ResponseEntity} containing an HTTP status code
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deletePaymentData(@PathVariable("id") Long id) {
        paymentDataService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles a DELETE request to delete all payment data.
     *
     * <p><strong>Note:</strong> This method is only intended for testing purposes.</p>
     *
     * @return a {@link ResponseEntity} containing an HTTP status code
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAllPaymentData() {
        paymentDataService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

