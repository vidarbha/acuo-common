package com.acuo.common.model.results;

import lombok.Data;

@Data
public class ReutersError extends Error {
    private String errorType;
    private String errorMessage;
}