package com.acuo.common.model.results;

import lombok.Data;

@Data
public class ClarusError {
    private String message;
}

// For lack of a better error, we now extract each message.