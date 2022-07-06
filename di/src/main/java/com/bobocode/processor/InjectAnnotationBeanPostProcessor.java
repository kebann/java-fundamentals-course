package com.bobocode.processor;

import com.bobocode.annotation.Inject;
import com.bobocode.annotation.Qualifier;
import com.bobocode.context.ApplicationContext;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectAnnotationBeanPostProcessor implements BeanPostProcessor {
    @SneakyThrows
    @Override
    public void configure(Object bean, ApplicationContext context) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);

                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object dependency = qualifier == null ? context.getBean(field.getType()) :
                        context.getBean(field.getType(), qualifier.value());

                field.set(bean, dependency);
            }
        }
    }
}
