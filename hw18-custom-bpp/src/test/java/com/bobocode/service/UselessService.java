package com.bobocode.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UselessService extends TrimmableService {
    public String addTrailingSpace(String input) {
        return input + "  ";
    }

    public String[] trimArray(String[] input) {
        return input;
    }

    public List<String> trimList(List<String> strings, List<Integer> integers) {
        return strings;
    }
}