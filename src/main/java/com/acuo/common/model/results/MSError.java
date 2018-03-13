package com.acuo.common.model.results;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MSError extends Error {
    private Integer errorCode;
    private String errorDescription;
    private String errorMessage;
    private String statusCode;
    private String httpStatusDescription;
}

