package com.example.demo.model;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class Total {
    private AtomicReference<Integer> total;
}
