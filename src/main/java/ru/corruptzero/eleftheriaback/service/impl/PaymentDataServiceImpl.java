package ru.corruptzero.eleftheriaback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.domain.repository.PaymentDataRepository;
import ru.corruptzero.eleftheriaback.exception.EmptyEntityListException;
import ru.corruptzero.eleftheriaback.exception.EntityDeleteException;
import ru.corruptzero.eleftheriaback.exception.EntityNotFoundException;
import ru.corruptzero.eleftheriaback.service.PaymentDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class PaymentDataServiceImpl implements PaymentDataService {
    @Autowired
    private PaymentDataRepository paymentDataRepository;

    public List<PaymentData> getAllPaymentData() {
        List<PaymentData> paymentData = new ArrayList<>(paymentDataRepository.findAll());
        if (paymentData.isEmpty()) {
            throw new EmptyEntityListException("No payment data found.");
        }
        return paymentData;
    }

    public Optional<PaymentData> findById(Long id) {
        Optional<PaymentData> paymentData = paymentDataRepository.findById(id);
        if (paymentData.isEmpty()) {
            throw new EntityNotFoundException("Payment data not found with ID: " + id);
        }
        return paymentData;
    }

    public PaymentData save(PaymentData paymentData) {
        return paymentDataRepository.save(paymentData);
    }

    public void deleteById(Long id) {
        try {
            paymentDataRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityDeleteException("Failed to delete payment data with ID: " + id + ". Entity not found.");
        } catch (DataAccessException e) {
            throw new EntityDeleteException("Failed to delete payment data with ID: " + id + ". Data access error occurred.");
        }
    }

    public void deleteAll() {
        try {
            paymentDataRepository.deleteAll();
        } catch (DataAccessException e) {
            throw new EntityDeleteException("Failed to delete all payment data. Data access error occurred.");
        }
    }
}