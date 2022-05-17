package com.bobocode.hw5;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public enum PersonKeyExtractor implements Function<Person, Comparable> {
    AGE_EXTRACTOR("age", Person::getAge),
    FIRST_NAME_EXTRACTOR("fistName", Person::getFistName),
    MIDDLE_NAME_EXTRACTOR("middleName", Person::getMiddleName),
    LAST_NAME_EXTRACTOR("lastName", Person::getLastName),
    EMAIL_EXTRACTOR("email", Person::getEmail),
    BIRTHDATE_EXTRACTOR("birthday", Person::getBirthday);

    private final String fieldName;
    private final Function<Person, Comparable<?>> keyExtractor;

    public static Function<Person, Comparable> findExtractorByFieldName(@NonNull Field field) {
        return Arrays.stream(PersonKeyExtractor.values())
                .filter(keyExtractor -> keyExtractor.getFieldName().equals(field.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("No extractor found for field %s", field.getName())));
    }

    @Override
    public Comparable<?> apply(Person t) {
        return keyExtractor.apply(t);
    }
}