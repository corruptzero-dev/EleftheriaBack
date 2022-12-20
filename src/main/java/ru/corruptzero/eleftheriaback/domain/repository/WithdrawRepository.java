package ru.corruptzero.eleftheriaback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.corruptzero.eleftheriaback.domain.entity.Withdraw;

import java.util.List;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
    @Query("select w from Withdraw w where w.status = :Pending")
    List<Withdraw> findPendingWithdraws();
}
