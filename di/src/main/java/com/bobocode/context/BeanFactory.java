package com.bobocode.context;

import com.bobocode.definition.BeanDefinition;
import com.bobocode.exception.BeanInstantiationException;
import com.bobocode.processor.BeanPostProcessor;
import com.bobocode.scanner.BeanPostProcessorScanner;

import java.util.Set;

public class BeanFactory {
    private final ApplicationContext context;
    private final Set<BeanPostProcessor> beanPostProcessors;

    public BeanFactory(AnnotationApplicationContext context, String packageName) {
        this.context = context;
        beanPostProcessors = BeanPostProcessorScanner.scan(packageName);
    }

    public Object createBean(BeanDefinition beanDefinition) {
        try {
            var constructor = beanDefinition.getBeanClass().getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new BeanInstantiationException("Bean instantiation failed with: ", e);
        }
    }

    public void configureBean(Object bean) {
        beanPostProcessors.forEach(postProcessors -> postProcessors.configure(bean, context));
    }
}
