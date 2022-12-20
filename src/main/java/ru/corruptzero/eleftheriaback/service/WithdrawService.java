package ru.corruptzero.eleftheriaback.service;

import ru.corruptzero.eleftheriaback.domain.entity.Withdraw;

import java.util.List;
import java.util.Optional;

public interface WithdrawService {
    Withdraw save(Withdraw withdraw);
    List<Withdraw> getAllWithdraws();
    Optional<Withdraw> findById(Long id);
    void deleteById(Long id);
    void deleteAll();
}
