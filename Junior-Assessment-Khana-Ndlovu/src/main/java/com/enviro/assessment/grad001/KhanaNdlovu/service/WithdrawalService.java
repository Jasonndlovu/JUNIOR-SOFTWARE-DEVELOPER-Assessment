package com.enviro.assessment.grad001.KhanaNdlovu.service;

import com.enviro.assessment.grad001.KhanaNdlovu.model.Withdrawal;
import com.enviro.assessment.grad001.KhanaNdlovu.repo.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WithdrawalService {
    @Autowired
    WithdrawalRepository withdrawalRepository;

    public List<Withdrawal> findAll(){
        return (List<Withdrawal>) withdrawalRepository.findAll();
    }

    public List<Withdrawal> findByCreatedAtBetween(Date startDate, Date endDate) {
        return withdrawalRepository.findByCreatedAtBetween(startDate, endDate);
    }
}
