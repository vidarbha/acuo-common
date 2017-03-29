package com.acuo.common.model.assets;

import lombok.Data;

@Data
public class AssetError {
    private String assetId;
    private String errorType;
    private String errorMessage;
}
