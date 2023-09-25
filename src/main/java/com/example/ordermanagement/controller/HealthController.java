package com.example.ordermanagement.controller;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "Health service controller")
@RestController
public class HealthController {

    @GetMapping("/v1/health")
    public ResponseEntity<?> healthResource() {
        return new ResponseEntity<>("Order Management Service is Up", HttpStatus.OK);
    }
}
