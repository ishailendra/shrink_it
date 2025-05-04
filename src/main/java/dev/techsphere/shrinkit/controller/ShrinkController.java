package dev.techsphere.shrinkit.controller;

import dev.techsphere.shrinkit.model.ErrorResponse;
import dev.techsphere.shrinkit.model.ShrinkRequest;
import dev.techsphere.shrinkit.model.ShrinkUrlInfoRequest;
import dev.techsphere.shrinkit.service.ShrinkService;
import dev.techsphere.shrinkit.service.UrlAiService;
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

    @Autowired
    private UrlAiService aiService;

    @PostMapping(value = "shrink")
    public ResponseEntity<?> shortenUrl(@RequestBody ShrinkRequest request) {
        try {
            return new ResponseEntity<>(service.shrinkUrl(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "{shrinkCode}")
    public ResponseEntity<?> getUrlAndRedirect(@PathVariable String shrinkCode) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                                .location(URI.create(service.getOriginalUrl(shrinkCode)))
                                .build();
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get/info")
    public ResponseEntity<?> getShrinkUrlInfo(@RequestBody ShrinkUrlInfoRequest request) {
        try {
            return new ResponseEntity<>(service.shrinkUrlInfo(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
