package com.acuo.common.model.results;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImportError extends Error {
    private String Sheet;
    private String variable;
    private String lineNumber;
    private String portfolioId;
}