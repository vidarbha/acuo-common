package com.acuo.common.model.results;

import lombok.Data;

@Data
public class MSError {
    private Integer errorCode;
    private String errorDescription;
    private String errorMessage;
    private String statusCode;
    private String httpStatusDescription;
}
