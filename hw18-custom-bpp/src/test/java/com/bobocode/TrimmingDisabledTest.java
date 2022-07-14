package com.bobocode;


import com.bobocode.config.DisabledTrimmingConfig;
import com.bobocode.service.UselessService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = DisabledTrimmingConfig.class)
class TrimmingDisabledTest {

    @DisplayName("""
            Check that method args and return value of String type are not trimmed
            when type is annotated with @Trimmed and @EnableStringTrimming is enabled
            """)
    @Test
    void testStringMethodArgsAndReturnValueAreNotTrimmed(@Autowired UselessService service) {
        String input = "  Not a random string  ";
        String actual = service.addTrailingSpace(input);

        assertEquals(input + "  ", actual);
    }
}
