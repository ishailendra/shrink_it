package com.shail.shrinkit.utils;

import com.shail.shrinkit.repository.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class ShrinkItUtils {

    @Autowired
    private UrlRepository repo;

    public String replaceSpacesInUrl(final String url) {
        if (StringUtils.isBlank(url)) return null;

        return url.replaceAll(" ", "+");
    }

    public String generateKey() {
        String key = null;

        boolean isKeyExist = true;

        while (isKeyExist) {
            key = RandomStringUtils.randomAlphanumeric(7);
            isKeyExist = repo.findByKey(key).isPresent();
        }
        return key;
    }

    public boolean isValidUrl (String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
