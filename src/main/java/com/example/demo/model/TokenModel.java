package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenModel {
    @JsonProperty("token")
    private String token;
}
