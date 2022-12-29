package ru.corruptzero.eleftheriaback.service;

import ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;
import ru.corruptzero.eleftheriaback.exception.EmptyEntityListException;
import ru.corruptzero.eleftheriaback.exception.EntityDeleteException;
import ru.corruptzero.eleftheriaback.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Service that provides CRUD operations for {@link Withdraw} objects.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public interface WithdrawService {

    /**
     * Saves a given withdraw.
     *
     * @param withdraw the withdraw to save
     * @return the saved withdraw
     */
    Withdraw save(Withdraw withdraw);

    /**
     * Returns a list of all withdraws.
     *
     * @return the list of all withdraws
     * @throws EmptyEntityListException if no withdraws are found
     */
    List<Withdraw> getAllWithdraws();

    /**
     * Returns the withdraw with the given ID, if it exists.
     *
     * @param id the ID of the withdraw to retrieve
     * @return the withdraw with the given ID, wrapped in an {@link Optional}
     * @throws EntityNotFoundException if the withdraw is not found
     */
    Optional<Withdraw> findById(Long id);

    /**
     * Returns a list of all withdraws by their status.
     *
     * @param status the status of the withdraw to retrieve
     * @return the list of all withdraws with a specified status
     * @throws EmptyEntityListException if no withdraws are found
     */
    List<Withdraw> findAllByStatus(EWithdrawStatus status);

    /**
     * Deletes the withdraw with the given ID.
     *
     * @param id the ID of the withdraw to delete
     * @throws EntityDeleteException if the withdraw cannot be deleted
     */
    void deleteById(Long id);

    /**
     * Deletes all withdraws.
     * <p>
     * <strong>Note:</strong> This method is only intended for testing purposes.
     *
     * @throws EntityDeleteException if the withdraws cannot be deleted
     */
    void deleteAll();
}
