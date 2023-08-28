package com.enviro.assessment.grad001.KhanaNdlovu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DateRangeRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
