package com.bobocode.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.IntUnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

public class ImplementationOfFunctionalInterfaceTest {

    static class LambdaHolder {
        IntUnaryOperator absLambda = num -> Math.abs(num);

        private static void method1() {
        }

        private void method2() {
        }

        public void method3() {
        }
    }

    static class MethodRefHolder {
        IntUnaryOperator absMethodRef = Math::abs;

        private static void method1() {
        }

        private void method2() {
        }

        public void method3() {
        }
    }

    @DisplayName("Verify that functional interface is implemented using lambda expression")
    @Test
    void verifyFuncInterfaceIsImplementedUsingLambdaExpression() {
        Method[] declaredMethods = LambdaHolder.class.getDeclaredMethods();
        assertThat(declaredMethods)
                .filteredOn(Method::isSynthetic)
                .singleElement()
                .satisfies(method -> {
                    assertThat(Modifier.isStatic(method.getModifiers())).isTrue();
                    assertThat(Modifier.isPrivate(method.getModifiers())).isTrue();
                    assertThat(method.getName()).startsWith("lambda");
                });
    }

    @DisplayName("Verify that functional interface is implemented using method reference")
    @Test
    void verifyFuncInterfaceIsImplementedUsingMethodRef() {
        Method[] declaredMethods = MethodRefHolder.class.getDeclaredMethods();
        assertThat(declaredMethods).noneMatch(Method::isSynthetic);
    }
}
