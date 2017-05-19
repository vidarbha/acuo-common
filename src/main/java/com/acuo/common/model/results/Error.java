package com.acuo.common.model.results;

import lombok.Data;

@Data
public class Error {
    private String objectId;
    private String errorType;
    private String errorMessage;
}
