package com.acuo.common.model.margin;

import com.acuo.common.model.margin.Types.SecurityIdType;
import com.acuo.common.util.Utils;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Pledge implements Comparable<Pledge>{

    private String callAmpId;
    private int rejectReasonCode;
    private String securityId;
    private SecurityIdType securityIdType;
    private LocalDate settlementDate;
    private double currentMarketValue;

    @Override
    public int compareTo(Pledge other) {
        return Utils.nullSafeStringComparator.compare(this.callAmpId, other.callAmpId);
    }
}
