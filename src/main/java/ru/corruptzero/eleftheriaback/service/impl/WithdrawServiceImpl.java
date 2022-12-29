package ru.corruptzero.eleftheriaback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;
import ru.corruptzero.eleftheriaback.domain.repository.WithdrawRepository;
import ru.corruptzero.eleftheriaback.dto.WithdrawDTO;
import ru.corruptzero.eleftheriaback.exception.EmptyEntityListException;
import ru.corruptzero.eleftheriaback.exception.EntityDeleteException;
import ru.corruptzero.eleftheriaback.exception.EntityNotFoundException;
import ru.corruptzero.eleftheriaback.mapper.WithdrawMapper;
import ru.corruptzero.eleftheriaback.service.WithdrawService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class WithdrawServiceImpl implements WithdrawService {
    @Autowired
    private WithdrawRepository withdrawRepository;

    private final WithdrawMapper mapper = WithdrawMapper.INSTANCE;

    public List<Withdraw> getAllWithdraws() {
        List<Withdraw> withdraws = new ArrayList<>(withdrawRepository.findAll());
        if (withdraws.isEmpty()) {
            throw new EmptyEntityListException("No withdraws found.");
        }
        return withdraws;
    }

    public Optional<Withdraw> findById(Long id) {
        Optional<Withdraw> withdraw = withdrawRepository.findById(id);
        if (withdraw.isEmpty()) {
            throw new EntityNotFoundException("Withdraw not found with ID: " + id);
        }
        return withdraw;
    }

    @Override
    public List<Withdraw> findAllByStatus(EWithdrawStatus status) {
        List<Withdraw> withdraws = new ArrayList<>(withdrawRepository.findAllByStatus(status));
        if (withdraws.isEmpty()) {
            throw new EmptyEntityListException("No pending withdraws found.");
        }
        return withdraws;
    }

    public Withdraw save(Withdraw withdraw) {
        return withdrawRepository.save(withdraw);
    }


    public void deleteById(Long id) {
        try {
            withdrawRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityDeleteException("Failed to delete withdraw with ID: " + id + ". Entity not found.");
        } catch (DataAccessException e) {
            throw new EntityDeleteException("Failed to delete withdraw with ID: " + id + ". Data access error occurred.");
        }
    }

    public void deleteAll() {
        try {
            withdrawRepository.deleteAll();
        } catch (DataAccessException e) {
            throw new EntityDeleteException("Failed to delete all withdraws. Data access error occurred.");
        }
    }
}
