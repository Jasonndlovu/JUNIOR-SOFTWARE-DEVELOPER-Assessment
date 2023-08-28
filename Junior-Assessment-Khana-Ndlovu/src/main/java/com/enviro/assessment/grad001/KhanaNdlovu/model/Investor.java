package com.enviro.assessment.grad001.KhanaNdlovu.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="Investors")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Investor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private int age;

    @Column
    private String product;
    @Column
    private BigDecimal balance; // Add the balance attribute

    public BigDecimal getBalance() {
        return balance; // Return the current balance
    }

    public void setBalance(BigDecimal newBalance) {
        this.balance = newBalance; // Set the new balance
    }

    public int getAge() {
        return age;  // Return the user's age
    }

    public String getProdut() {
        return product; // Return the user's chosen Product
    }

    public long getId() {
        return id;
    }
}





