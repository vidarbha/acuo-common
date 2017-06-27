package com.acuo.common.model.results;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClarusError extends Error {

    // For lack of a better error, we now extract each message.
    private String message;
}


