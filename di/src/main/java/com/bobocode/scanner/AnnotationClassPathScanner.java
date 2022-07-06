package com.bobocode.scanner;

import com.bobocode.annotation.Bean;
import com.bobocode.definition.BeanDefinition;
import lombok.experimental.UtilityClass;
import org.reflections.Reflections;

import java.util.List;

/**
 * Classpath scanner that scans for types annotated with {@link Bean} within a given package.
 */
@UtilityClass
public class AnnotationClassPathScanner {

    public List<BeanDefinition> scan(String packageName) {
        Reflections reflections = new Reflections(packageName);

        return reflections.getTypesAnnotatedWith(Bean.class)
                .stream()
                .map(BeanDefinition::from)
                .toList();
    }
}
