package com.shail.shrinkit.service;

import com.shail.shrinkit.entity.ShrinkEntity;
import com.shail.shrinkit.model.ShrinkRequest;
import com.shail.shrinkit.model.ShrinkResponse;
import com.shail.shrinkit.repository.UrlRepository;
import com.shail.shrinkit.utils.ShrinkItUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ShrinkService {

    @Autowired
    private ShrinkItUtils utils;

    @Autowired
    private UrlRepository repo;

    @Value("${shrink.base.url}")
    private String baseUrl;


    @Cacheable(value = "urls", key = "#request.url") //, key="#url.key"
    public ShrinkResponse shrinkUrl(ShrinkRequest request) {
        ShrinkResponse response = new ShrinkResponse();
        response.setUrl(request.getUrl());

        try {
            String url = utils.replaceSpacesInUrl(request.getUrl());

            if (!utils.isValidUrl(url)) {
                response.setValidUrl(false);
                response.setErrorDesc("INVALID_URL");
                return response;
            }

            Optional<ShrinkEntity> optional = repo.findByUrl(url);

            if (optional.isPresent()) {
                response.setShortenUrl(generateUrl(optional.get().getKey()));
                response.setKey(optional.get().getKey());
                return response;
            }

            ShrinkEntity entity = saveShrinkUrl(url);

            response.setShortenUrl(generateUrl(entity.getKey()));
            response.setKey(entity.getKey());
            response.setValidUrl(true);
            return response;

        } catch (Exception e) {
            response.setValidUrl(false);
            response.setErrorDesc(e.getMessage());
            return response;
        }

    }

    private String generateUrl(String key) {
        StringBuilder shortUrl = new StringBuilder();
        shortUrl.append(baseUrl);
        shortUrl.append(key);
        return shortUrl.toString();
    }

    @Cacheable(value = "shrink_key", key = "#shrinkKey")
    public String getFullUrl(String shrinkKey) {
        try {
            return repo.findByKey(shrinkKey).get().getUrl();
        } catch (Exception e) {
            return null;
        }
    }

    private ShrinkEntity saveShrinkUrl(String url) {
        String key = utils.generateKey();
        ShrinkEntity entity = new ShrinkEntity();
        entity.setUrl(url);
        entity.setKey(key);

        return repo.save(entity);
    }

    public String getShrinkKey(String url) {
        String[] arr = url.split("/");
        return ArrayUtils.isEmpty(arr) ? null : arr[arr.length - 1];
    }
}
