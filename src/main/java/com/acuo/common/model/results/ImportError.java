package com.acuo.common.model.results;

import lombok.Data;

@Data
public class ImportError extends Error {
    private String Sheet;
    private String variable;
    private String lineNumber;
}
