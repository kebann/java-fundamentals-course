package com.bobocode.scanner;

import com.bobocode.processor.BeanPostProcessor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Classpath scanner that scans for implementations of {@link BeanPostProcessor}
 */
@UtilityClass
public class BeanPostProcessorScanner {

    private static final String DEFAULT_POST_PROCESSOR_PACKAGE = "com.bobocode.processor";

    @SneakyThrows
    public Set<BeanPostProcessor> scan(@NonNull String packageName) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(DEFAULT_POST_PROCESSOR_PACKAGE, packageName));
        var postProcessorsClasses = reflections.getSubTypesOf(BeanPostProcessor.class);

        Set<BeanPostProcessor> postProcessors = new HashSet<>();
        for (var postProcessorClass : postProcessorsClasses) {
            var constructor = postProcessorClass.getConstructor();
            postProcessors.add(constructor.newInstance());
        }

        return postProcessors;
    }
}
