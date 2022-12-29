package ru.corruptzero.eleftheriaback.service;

import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.exception.EmptyEntityListException;
import ru.corruptzero.eleftheriaback.exception.EntityDeleteException;
import ru.corruptzero.eleftheriaback.exception.EntityNotFoundException;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;

import java.util.List;
import java.util.Optional;

/**
 * Service that provides CRUD operations for {@link User} objects.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public interface UserService {

    /**
     * Saves a given user.
     *
     * @param user the user to save
     * @return the saved user
     * @throws InvalidEntityException if the user is invalid or has the same email as another user
     */
    User save(User user);

    /**
     * Returns a list of all users.
     *
     * @return the list of all users
     * @throws EmptyEntityListException if no users are found
     */
    List<User> findAll();

    /**
     * Returns the user with the given ID, if it exists.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the given ID, wrapped in an {@link Optional}
     * @throws EntityNotFoundException if the user is not found
     */
    Optional<User> findById(Long id);

    /**
     * Deletes the user with the given ID.
     *
     * @param id the ID of the user to delete
     * @throws EntityDeleteException if the user cannot be deleted
     */
    void deleteById(Long id);

    /**
     * Deletes all users.
     * <p>
     * <strong>Note:</strong> This method is only intended for testing purposes.
     *
     * @throws EntityDeleteException if the users cannot be deleted
     */
    void deleteAll();
}
