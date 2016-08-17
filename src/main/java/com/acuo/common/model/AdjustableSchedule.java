package com.acuo.common.model;

import com.opengamma.strata.basics.schedule.Frequency;
import lombok.Data;

@Data
public class AdjustableSchedule extends AdjustableDate {

    private Frequency frequency;

}
