package com.bobocode.context;

import com.bobocode.annotation.Qualifier;
import com.bobocode.definition.BeanDefinition;
import com.bobocode.exception.NoSuchBeanException;
import com.bobocode.exception.NoUniqueBeanException;

import java.util.Map;

/**
 * The root interface for accessing the DI container
 */
public interface ApplicationContext {

    /**
     * Retrieves a bean by type from the context.
     *
     * @param beanType class of the target bean
     * @param <T>      type of the target bean
     * @return bean of type T fetched from the context
     * @throws NoSuchBeanException   if no bean found for the target type
     * @throws NoUniqueBeanException if more than one bean with provided type is found
     */
    <T> T getBean(Class<T> beanType);

    /**
     * Retrieves a bean by its name and type.
     *
     * @param name     name of the target bean
     * @param beanType type of the target bean
     * @param <T>      type of the target bean
     * @return bean of type T fetched from the context
     * @throws NoSuchBeanException if no bean found for the target type
     */
    <T> T getBean(String name, Class<T> beanType);

    /**
     * Returns a {@link Map} with all the beans of the target type
     * where bean's name is a key and bean's instance is a value.
     *
     * @param beanType type of the target bean
     * @param <T>      type of the target bean
     * @return a {@link Map} of all beans with the provided type. If no beans are found returns empty {@link Map}
     */
    <T> Map<BeanDefinition, T> getAllBeans(Class<T> beanType);

    /**
     * Retrieves a bean by its {@link Qualifier#value()} and type.
     *
     * @param qualifier {@link Qualifier#value()} of the target bean
     * @param beanType  type of the target bean
     * @param <T>       type of the target bean
     * @return bean of type T fetched from the context
     * @throws NoSuchBeanException   if no bean found for the target type
     * @throws NoUniqueBeanException if more than one bean with provided type is found
     */
    <T> T getBean(Class<T> beanType, String qualifier);
}
