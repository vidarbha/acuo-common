package com.acuo.common.model;

import com.opengamma.strata.basics.schedule.Frequency;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdjustableSchedule extends AdjustableDate {

    private Frequency frequency;

}
