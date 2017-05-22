package com.acuo.common.model.results;

import lombok.Data;

@Data
public class Error {
    private String assetId;
    private String errorType;
    private String errorMessage;
}
