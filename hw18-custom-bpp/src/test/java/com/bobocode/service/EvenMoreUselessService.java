package com.bobocode.service;

import org.springframework.stereotype.Service;

@Service
public class EvenMoreUselessService {
    public String addLeadingSpace(String input) {
        return "  " + input;
    }
}