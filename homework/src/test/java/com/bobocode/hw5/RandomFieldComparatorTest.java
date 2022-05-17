package com.bobocode.hw5;

import com.bobocode.model.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RandomFieldComparatorTest {

    static Stream<RandomFieldComparator<Person>> comparators() {
        return Stream.of(new RandomFieldComparator<>(Person.class, false),
                new RandomFieldComparator<>(Person.class, true));
    }

    @ParameterizedTest
    @MethodSource(value = "comparators")
    void testRandomFieldComparator(RandomFieldComparator<Person> randomFieldComparator) {
        List<Person> people = Person.generatePersonList(10);
        people.sort(randomFieldComparator);

        var randomField = randomFieldComparator.getRandomField();
        var keyExtractor = PersonKeyExtractor.findExtractorByFieldName(randomField);

//       verifying that the list sorted by the random comparator
//       matches the sorting done by the java's native comparator
        Assertions.assertThat(people)
                .isSortedAccordingTo(comparing(keyExtractor));
    }

    @DisplayName("Verify that IllegalArgumentException is thrown if no fields are available for comparison ")
    @Test
    void testExceptionIsThrownIfNoComparableFieldsAreAvailable() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new RandomFieldComparator<>(Account.class));

        Assertions.assertThat(exception).hasMessage("No field is available for comparison");
    }
}