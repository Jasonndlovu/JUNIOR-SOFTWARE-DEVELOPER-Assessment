package com.enviro.assessment.grad001.KhanaNdlovu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="Withdrawal")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long investorId;
    @Column
    private BigDecimal withdrawalAmount;

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    public void setInvestor(Investor investor) {
        this.investorId = investor.getId();
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }


}
