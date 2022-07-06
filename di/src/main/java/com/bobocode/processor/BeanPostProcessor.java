package com.bobocode.processor;

import com.bobocode.context.ApplicationContext;

/**
 * The hook that allows for custom modification of new bean instances.
 * For example, checking for marker interfaces or wrapping beans with proxies.
 */
public interface BeanPostProcessor {
    void configure(Object bean, ApplicationContext context);
}