package com.enviro.assessment.grad001.KhanaNdlovu.controller;

import com.enviro.assessment.grad001.KhanaNdlovu.dto.DateRangeRequest;
import com.enviro.assessment.grad001.KhanaNdlovu.model.Withdrawal;
import com.enviro.assessment.grad001.KhanaNdlovu.service.WithdrawalService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Controller
public class WithdrawalController {

    @Autowired
    WithdrawalService withdrawalService;


    @PostMapping("/csvExportRange")
    public void exportCSVByDateRange(@RequestBody DateRangeRequest dateRangeRequest, HttpServletResponse response) throws Exception {
        // Extract start and end dates from the DateRangeRequest object
        System.out.println(dateRangeRequest);
        System.out.println("here");


        Date startDate = dateRangeRequest.getStartDate();
        Date endDate = dateRangeRequest.getEndDate();

        // Set file name and content type
        String filename = "Withdrawal-data.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        // Get withdrawal data within the specified date range
        List<Withdrawal> withdrawals = withdrawalService.findByCreatedAtBetween(startDate, endDate);

        // Create a CSV writer
        StatefulBeanToCsv<Withdrawal> writer = new StatefulBeanToCsvBuilder<Withdrawal>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false)
                .build();

        // Write filtered withdrawal data to the CSV file
        System.out.println(withdrawals);

        writer.write(withdrawals);
    }

    @PostMapping("/findByDateRange")
    public ResponseEntity<List<Withdrawal>> findByDateRange(@RequestBody DateRangeRequest dateRangeRequest) {
        Date startDate = dateRangeRequest.getStartDate();
        Date endDate = dateRangeRequest.getEndDate();

        List<Withdrawal> withdrawals = withdrawalService.findByCreatedAtBetween(startDate, endDate);

        if (withdrawals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(withdrawals, HttpStatus.OK);
        }
    }

    @GetMapping("/csvexport")
    public void exportCSV(HttpServletResponse response)
            throws Exception {

        //set file name and content type
        String filename = "Withdrawal-data.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        //create a csv writer
        StatefulBeanToCsv<Withdrawal> writer = new StatefulBeanToCsvBuilder<Withdrawal>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false)
                .build();
        //write all employees data to csv file
        writer.write(withdrawalService.findAll());

    }

}
