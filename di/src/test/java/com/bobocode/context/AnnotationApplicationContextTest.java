package com.bobocode.context;

import com.bobocode.beans.AuthController;
import com.bobocode.beans.JokeService;
import com.bobocode.beans.MissingBean;
import com.bobocode.exception.NoSuchBeanException;
import com.bobocode.exception.NoUniqueBeanException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationApplicationContextTest {

    @Test
    void testDependenciesProperlyInjected() {
        var context = new AnnotationApplicationContext("com.bobocode.beans");
        AuthController authController = context.getBean(AuthController.class);

        assertNotNull(authController);
        assertNotNull(authController.getAuthService());
        assertNotNull(authController.getJokeService());
    }

    @Test
    void testBeanFetchedByName() {
        var context = new AnnotationApplicationContext("com.bobocode.beans");
        JokeService jokeService = context.getBean("funnyBean", JokeService.class);

        assertNotNull(jokeService);
    }

    @Test
    void testExceptionIsThrownIfMultipleBeansAvailableWithoutQualifiers() {
        assertThrows(
                NoUniqueBeanException.class,
                () -> new AnnotationApplicationContext("com.bobocode.missingqualifiers"),
                """
                        Multiple implementations:
                        [com.bobocode.missingqualifiers.JsonParser,com.bobocode.missingqualifiers.XmlParser]
                        are found for type com.bobocode.missingqualifiers.Parser
                        """
        );
    }

    @Test
    void testExceptionIsThrownIfNoBeanInContext() {
        var context = new AnnotationApplicationContext("com.bobocode.beans");
        var beanClass = MissingBean.class;

        assertThrows(
                NoSuchBeanException.class,
                () -> context.getBean(beanClass),
                String.format("No bean of class %s type is found", beanClass.getName())
        );
    }
}