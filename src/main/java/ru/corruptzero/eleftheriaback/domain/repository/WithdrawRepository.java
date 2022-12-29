package ru.corruptzero.eleftheriaback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;

import java.util.List;

/**
 * Repository interface for {@link Withdraw} entities.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {

    /**
     * Finds all {@link Withdraw} entities in the database by their status.
     *
     * @return a list of {@link Withdraw} entities with a specified status
     */
    @Query("select w from Withdraw w where w.status = :status")
    List<Withdraw> findAllByStatus(@Param("status") EWithdrawStatus status);
}