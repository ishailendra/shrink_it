package dev.techsphere.shrinkit.utils;

import dev.techsphere.shrinkit.model.ShrinkRequest;
import dev.techsphere.shrinkit.model.ShrinkUrlInfoRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class ValidationUtil {

    public boolean isRequestValid(ShrinkRequest request) {
        return ObjectUtils.isNotEmpty(request) && StringUtils.isNotBlank(request.getOriginalUrl());
    }

    public boolean isShrinkInfoRequestValid(ShrinkUrlInfoRequest request) {
        return ObjectUtils.isNotEmpty(request) && StringUtils.isNotBlank(request.getShrinkUrl());
    }

    public boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
