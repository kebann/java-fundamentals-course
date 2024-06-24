package com.trimmer.bpp;

import com.trimmer.annotation.Trimmed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Trimmed.class)) {
            return proxy(bean);
        }
        return bean;
    }

    private Object proxy(Object bean) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback(createInterceptor());
        return enhancer.create();
    }

    private MethodInterceptor createInterceptor() {
        return (obj, method, args, proxy) -> {
            Object res = proxy.invokeSuper(obj, processArgs(args));
            return res instanceof String s ? s.trim() : res;
        };
    }

    private Object[] processArgs(Object[] args) {
        return Arrays.stream(args)
                .map(this::trimStringArg)
                .toArray();

    }

    private Object trimStringArg(Object arg) {
        return switch (arg) {
            case String s -> s.trim();
//            TODO: change how the generic type is checked
            case List l && l.get(0) instanceof String -> ((List<String>) l).stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .toList();
            case String[] arr -> Arrays.stream(arr)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .toArray(String[]::new);
            default -> arg;
        };
    }
}
