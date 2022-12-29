package ru.corruptzero.eleftheriaback.service;

import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.exception.EmptyEntityListException;
import ru.corruptzero.eleftheriaback.exception.EntityDeleteException;
import ru.corruptzero.eleftheriaback.exception.EntityNotFoundException;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;

import java.util.List;
import java.util.Optional;

/**
 * Service that provides CRUD operations for {@link PaymentData} objects.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public interface PaymentDataService {
    /**
     * Saves a given payment data.
     *
     * @param paymentData the payment data to save
     * @return the saved payment data
     * @throws InvalidEntityException if the payment data is invalid
     */
    PaymentData save(PaymentData paymentData);

    /**
     * Returns a list of all payment data.
     *
     * @return the list of all payment data
     * @throws EmptyEntityListException if no payment data are found
     */
    List<PaymentData> getAllPaymentData();

    /**
     * Returns the payment data with the given ID, if it exists.
     *
     * @param id the ID of the payment data to retrieve
     * @return the payment data with the given ID, wrapped in an {@link Optional}
     * @throws EntityNotFoundException if the payment data is not found
     */
    Optional<PaymentData> findById(Long id);

    /**
     * Deletes the payment data with the given ID.
     *
     * @param id the ID of the payment data to delete
     * @throws EntityDeleteException if the payment data cannot be deleted
     */
    void deleteById(Long id);

    /**
     * Deletes all payment data.
     * <p>
     * <strong>Note:</strong> This method is only intended for testing purposes.
     *
     * @throws EntityDeleteException if the payment data cannot be deleted
     */
    void deleteAll();

}
