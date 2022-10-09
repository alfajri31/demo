package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OrderController {

    @PostMapping(value="order")
    public ResponseEntity<?> order() {
        return ResponseEntity.ok().body("fs");
    }

}
