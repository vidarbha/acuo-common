package com.acuo.common.model.results;

import lombok.Data;

/**
 * Created by lucie on 23/5/17.
 */
@Data
public class ImportError {
    private String product;
    private String errorMessage;
    private String errorPath;

}
