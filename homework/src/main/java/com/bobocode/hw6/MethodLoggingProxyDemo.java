package com.bobocode.hw6;

import lombok.SneakyThrows;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class MethodLoggingProxyDemo {

    static class Person {
        @LogInvocation
        void sayHi() {
            System.out.println("Hi there!");
        }

        void sayBye() {
            System.out.println("I gotta go!");
        }
    }

    public static void main(String[] args) {
        Person proxy = createMethodLoggingProxy(Person.class);
        proxy.sayHi();
        proxy.sayBye();
    }

    /**
     * Creates a proxy of the provided class that logs its method invocations. If a method that
     * is marked with {@link LogInvocation} annotation is invoked, it prints to the console the following statement:
     * "[PROXY: Calling method '%s' of the class '%s']%n", where the params are method and class names accordingly.
     *
     * @param targetClass a class that is extended with proxy
     * @param <T>         target class type parameter
     * @return an instance of a proxy class
     */
    @SneakyThrows
    public static <T> T createMethodLoggingProxy(Class<T> targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);

        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.isAnnotationPresent(LogInvocation.class)) {
                System.out.printf("[PROXY: Calling method '%s' of the class '%s']%n", method.getName(), targetClass.getName());
                return proxy.invokeSuper(obj, args);
            }

            throw new UnsupportedOperationException();
        });

        return targetClass.cast(enhancer.create());
    }
}
