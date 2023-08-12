package com.shail.shrinkit.controller;

import com.shail.shrinkit.model.ShrinkRequest;
import com.shail.shrinkit.model.ShrinkResponse;
import com.shail.shrinkit.service.ShrinkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ShrinkController {

    @Autowired
    private ShrinkService service;

    @PostMapping(value = "shrink")
    public ResponseEntity<?> shortenUrl(@RequestBody ShrinkRequest request) {
        try {
            ShrinkResponse response = service.shrinkUrl(request);
            HttpStatus status = (response.isValidUrl() && StringUtils.isBlank(response.getErrorDesc())) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<ShrinkResponse>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "{shrinkKey}")
    public ResponseEntity<?> getUrlAndRedirect(@PathVariable String shrinkKey) {
        try {
            String url = service.getFullUrl(shrinkKey);

            String response = StringUtils.isBlank(url) ? "URL_NOT_FOUND" : url;
            HttpStatus status = StringUtils.isBlank(url) ? HttpStatus.NOT_FOUND : HttpStatus.OK;

            if (status.is2xxSuccessful())
                return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(response)).build();
            else
                return new ResponseEntity<String>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
