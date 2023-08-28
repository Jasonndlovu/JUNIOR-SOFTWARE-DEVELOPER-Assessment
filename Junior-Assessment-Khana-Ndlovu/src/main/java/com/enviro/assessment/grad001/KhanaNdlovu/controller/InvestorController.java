package com.enviro.assessment.grad001.KhanaNdlovu.controller;


import com.enviro.assessment.grad001.KhanaNdlovu.model.Investor;
import com.enviro.assessment.grad001.KhanaNdlovu.model.Withdrawal;
import com.enviro.assessment.grad001.KhanaNdlovu.repo.InvestorRepository;
import com.enviro.assessment.grad001.KhanaNdlovu.repo.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class InvestorController {


    @Autowired
    InvestorRepository investorRepository;
    @Autowired
    WithdrawalRepository withdrawalRepository;

    @PostMapping("/addInvestor")
    public ResponseEntity<Investor> addInvestor(@RequestBody Investor investor) {

        String tooyoung = "Investor must be at least 18 years old.";

        try {
            if(!investor.getProdut().equalsIgnoreCase("RETIREMENT") && !investor.getProdut().equalsIgnoreCase("SAVINGS"))
            { return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); }

            if (Objects.equals(investor.getProdut().toUpperCase(), "RETIREMENT") && investor.getAge() <= 65){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Investor investorObj = investorRepository.save(investor);
            return new ResponseEntity<>(investorObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addwithdrawl")
    public ResponseEntity<Withdrawal> addwithdrawl(@RequestBody Withdrawal withdrawal) {

        try {
            Withdrawal withdrawalObj = withdrawalRepository.save(withdrawal);
            return new ResponseEntity<>(withdrawalObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllInvestor")
    public ResponseEntity<List<Investor>> getAllInvestors() {
        try {
            List<Investor> investorList = new ArrayList<>();
            investorRepository.findAll().forEach(investorList::add);

            if (investorList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(investorList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping("/getAllWithdrawls")
        public ResponseEntity<List<Withdrawal>> getAllWithdrawls(){
            try {
                List<Withdrawal> withdrawalList = new ArrayList<>();
                withdrawalRepository.findAll().forEach(withdrawalList::add);

                if (withdrawalList.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

                }

                return new ResponseEntity<>(withdrawalList, HttpStatus.OK);
            } catch(Exception ex) {

                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


    @PostMapping("/withdraw/{investorId}")
    public ResponseEntity<Investor> withdraw(@PathVariable Long investorId, @RequestBody Withdrawal withdrawal) {
        try {
            System.out.println(withdrawal);

            // Retrieve the Investor by ID
            Optional<Investor> optionalInvestor = investorRepository.findById(investorId);

            if (optionalInvestor.isPresent()) {
                Investor investor = optionalInvestor.get();
                BigDecimal withdrawalAmount = withdrawal.getWithdrawalAmount();
                BigDecimal currentBalance = investor.getBalance();

                // Calculate 90% of the current balance
                BigDecimal maxWithdrawalAmount = currentBalance.multiply(new BigDecimal("0.9"));
                System.out.println(withdrawalAmount);
                System.out.println(currentBalance);
                System.out.println(maxWithdrawalAmount);

                int holder = withdrawalAmount.compareTo(maxWithdrawalAmount);



                if (holder >= 0) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

                }

                // Check if withdrawal amount is valid
                if (holder < 0) {
                    BigDecimal newBalance = currentBalance.subtract(withdrawalAmount);
                    investor.setBalance(newBalance);
                    Investor updatedInvestor = investorRepository.save(investor);

                    // Create a new Withdrawal instance and set its properties
                    Withdrawal newWithdrawal = new Withdrawal();
                    newWithdrawal.setInvestor(investor);
                    newWithdrawal.setWithdrawalAmount(withdrawalAmount);

                    System.out.println("Here!");
                    // Save the Withdrawal
                    Withdrawal updatedWithdrawal = withdrawalRepository.save(newWithdrawal);
                    System.out.println("Here!");
                    // You can perform additional actions with the updatedWithdrawal if needed.

                    return new ResponseEntity<>(updatedInvestor, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/getInvestorById/{id}")
    public ResponseEntity<Investor> getInvestorById(@PathVariable Long id) {
        Optional<Investor> investorObj = investorRepository.findById(id);
        if (investorObj.isPresent()) {
            return new ResponseEntity<>(investorObj.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
