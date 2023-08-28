package com.enviro.assessment.grad001.KhanaNdlovu.repo;


import com.enviro.assessment.grad001.KhanaNdlovu.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WithdrawalRepository  extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByCreatedAtBetween(Date startDate, Date endDate);
}
