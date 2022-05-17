package com.bobocode.hw5;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    private static final Faker FAKER = new Faker();

    public int age;
    public String fistName;
    public String middleName;
    private String lastName;
    private String email;
    public Date birthday;

    public static Person generatePerson() {
        return Person.builder()
                .age(FAKER.random().nextInt(100))
                .fistName(FAKER.name().firstName())
                .middleName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .email(FAKER.internet().emailAddress())
                .birthday(FAKER.date().birthday())
                .build();
    }

    public static List<Person> generatePersonList(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> Person.generatePerson())
                .collect(toList());
    }
}