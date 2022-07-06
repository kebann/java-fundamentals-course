package com.bobocode.context;

import com.bobocode.definition.BeanDefinition;
import com.bobocode.exception.NoSuchBeanException;
import com.bobocode.exception.NoUniqueBeanException;
import com.bobocode.scanner.AnnotationClassPathScanner;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;

/**
 * A simple implementation of {@link ApplicationContext} that scans the classpath
 * for types annotated with {@link com.bobocode.annotation.Bean}, adds them to context for management
 */
public class AnnotationApplicationContext implements ApplicationContext {
    private final Map<BeanDefinition, Object> container = new ConcurrentHashMap<>();
    private final BeanFactory beanFactory;

    public AnnotationApplicationContext(@NonNull String packageName) {
        this.beanFactory = new BeanFactory(this, packageName);
        AnnotationClassPathScanner
                .scan(packageName)
                .forEach(beanDefinition -> container.put(beanDefinition, beanFactory.createBean(beanDefinition)));
        container.values().forEach(beanFactory::configureBean);
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        List<Object> candidates = findCandidates(beanType);
        return getBean(beanType, candidates);
    }

    @Override
    public <T> T getBean(Class<T> beanType, String qualifier) {
        List<Object> candidates = findCandidates(beanType, qualifier);
        return getBean(beanType, candidates);
    }

    private <T> T getBean(Class<T> beanType, List<Object> candidates) {
        if (candidates.isEmpty()) {
            throw new NoSuchBeanException("No bean of " + beanType + " type is found");
        }

        if (candidates.size() > 1) {
            throw new NoUniqueBeanException(candidates, beanType);
        }

        return beanType.cast(candidates.get(0));
    }

    private List<Object> findCandidates(Class<?> beanType) {
        return container.values()
                .stream()
                .filter(o -> beanType.isAssignableFrom(o.getClass()))
                .toList();
    }

    private List<Object> findCandidates(Class<?> beanType, String qualifier) {
        return container.entrySet()
                .stream()
                .filter(e -> beanType.isAssignableFrom(e.getKey().getBeanClass())
                        && Objects.equals(e.getKey().getQualifier(), qualifier))
                .map(Map.Entry::getValue)
                .toList();
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        var definition = BeanDefinition.from(name, beanType);
        Object bean = container.get(definition);

        if (bean == null) {
            throw new NoSuchBeanException(String.format("No bean of %s type and with %s is found", beanType, name));
        }

        return beanType.cast(bean);
    }

    @Override
    public <T> Map<BeanDefinition, T> getAllBeans(Class<T> beanType) {
        return container.entrySet()
                .stream()
                .filter(e -> e.getValue().getClass().isAssignableFrom(beanType))
                .collect(toMap(Map.Entry::getKey, e -> beanType.cast(e.getValue())));
    }
}
