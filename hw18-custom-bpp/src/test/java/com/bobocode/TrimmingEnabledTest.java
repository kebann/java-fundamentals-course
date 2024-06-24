package com.bobocode;


import com.bobocode.config.EnabledTrimmingConfig;
import com.bobocode.service.EvenMoreUselessService;
import com.bobocode.service.UselessService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = EnabledTrimmingConfig.class)
class TrimmingEnabledTest {

    @DisplayName("""
            Check that method args and return value of String type are trimmed
            if @EnableStringTrimming is enabled and type is annotated with @Trimmed
            """)
    @Test
    void testStringMethodArgsAndReturnValueAreTrimmed(@Autowired UselessService service) {
        String input = "   Not a random string";
        String actual = service.addTrailingSpace(input);

        assertEquals(input.trim(), actual);
    }

    @Test
    void testMethodArgOfTypeStringArrayIsTrimmed(@Autowired UselessService service) {
        String[] input = {" 1 ", "2 ", "44", null, " adasqweq"};
        String[] actual = service.trimArray(input);

        String[] expected = Arrays.stream(input).filter(Objects::nonNull).map(String::trim).toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testMethodArgOfTypeListOfStringIsTrimmed(@Autowired UselessService service) {
        List<String> input = Arrays.asList(" 1 ", "2 ", "44", null, " adasqweq");
        List<String> actual = service.trimList(input, List.of(1, 2, 3));

        List<String> expected = input.stream().filter(Objects::nonNull).map(String::trim).toList();
        assertEquals(expected, actual);
    }

    @DisplayName("""
            Check that method args and return value of String type are not trimmed
            when type isn't annotated with @Trimmed and @EnableStringTrimming is enabled
            """)
    @Test
    void testStringMethodArgsAndReturnValueAreNotTrimmed(@Autowired EvenMoreUselessService service) {
        String input = "Not a random string  ";
        String actual = service.addLeadingSpace(input);

        assertEquals("  " + input, actual);
    }
}
