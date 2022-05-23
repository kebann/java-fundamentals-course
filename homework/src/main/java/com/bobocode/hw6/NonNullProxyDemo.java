package com.bobocode.hw6;

import lombok.SneakyThrows;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Parameter;

public class NonNullProxyDemo {

    static class Person {
        void sayHi(String name, @NonNull Integer age) {
            System.out.println("Hi there!");
        }

        void sayBye() {
            System.out.println("I gotta go!");
        }
    }

    public static void main(String[] args) {
        Person proxy = createMethodLoggingProxy(Person.class);
        proxy.sayHi(null, null);
    }

    @SneakyThrows
    public static <T> T createMethodLoggingProxy(Class<T> targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);

        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(NonNull.class) && args[i] == null) {
                    throw new NullPointerException(String.format("%s parameter is null", parameter.getName()));
                }
            }
            return proxy.invokeSuper(obj, args);
        });

        return targetClass.cast(enhancer.create());
    }
}
