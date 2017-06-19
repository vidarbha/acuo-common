package com.acuo.common.model.results;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReutersError extends Error {
    private String errorType;
    private String errorMessage;
}