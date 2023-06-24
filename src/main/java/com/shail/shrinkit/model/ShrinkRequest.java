package com.shail.shrinkit.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShrinkRequest {

    private String url;
    private String shortenUrl;
    private String key;

    private boolean isValidUrl;

    private String errorDesc;
}
