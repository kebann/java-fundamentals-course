package com.bobocode.exception;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class NoUniqueBeanException extends RuntimeException {
    public NoUniqueBeanException(String message) {
        super(message);
    }

    public NoUniqueBeanException(List<Object> candidates, Class<?> targetType) {
        super(formatMessage(candidates, targetType));
    }

    private static String formatMessage(List<Object> candidates, Class<?> targetType) {
        String candidateClassesStr = candidates.stream()
                .map(Object::getClass)
                .map(Class::getName)
                .collect(joining(","));

        return String.format("Multiple implementations:\n[%s]\nare found for type %s", candidateClassesStr, targetType.getName());
    }
}
