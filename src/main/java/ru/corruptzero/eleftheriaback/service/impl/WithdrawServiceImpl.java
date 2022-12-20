package ru.corruptzero.eleftheriaback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.corruptzero.eleftheriaback.domain.entity.Withdraw;
import ru.corruptzero.eleftheriaback.domain.repository.WithdrawRepository;
import ru.corruptzero.eleftheriaback.service.WithdrawService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawServiceImpl implements WithdrawService {
    @Autowired
    private WithdrawRepository withdrawRepository;

    public List<Withdraw> getAllWithdraws(){
        return new ArrayList<>(withdrawRepository.findAll());
    }

    public List<Withdraw> findPendingWithdraws(){
        return new ArrayList<>(withdrawRepository.findPendingWithdraws());
    }

    public Optional<Withdraw> findById(Long id){
        return withdrawRepository.findById(id);
    }

    public Withdraw save(Withdraw withdraw){
        return withdrawRepository.save(withdraw);
    }

    public void deleteById(Long id) {
        withdrawRepository.deleteById(id);
    }

    public void deleteAll() {
        withdrawRepository.deleteAll();
    }
}
