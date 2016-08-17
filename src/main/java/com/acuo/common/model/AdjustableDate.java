package com.acuo.common.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdjustableDate {

    private LocalDate date;
    private BusinessDayAdjustment adjustment;

}
