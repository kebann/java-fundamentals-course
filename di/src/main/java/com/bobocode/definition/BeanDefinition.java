package com.bobocode.definition;

import com.bobocode.annotation.Bean;
import com.bobocode.annotation.Qualifier;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Describes an information about a bean, how it should be instantiated and configured
 */
@Data
@Builder
@EqualsAndHashCode(of = {"name", "beanClass"})
public class BeanDefinition {
    private String name;
    private Class<?> beanClass;
    private String qualifier;

    public static BeanDefinition from(Class<?> type) {
        Bean beanAnnotation = type.getAnnotation(Bean.class);
        String beanName = beanAnnotation.value().isEmpty() ? type.getSimpleName() : beanAnnotation.value();
        String decapitalizedName = Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);

        var beanDefinitionBuilder = BeanDefinition.builder()
                .name(decapitalizedName)
                .beanClass(type);

        Qualifier qualifierAnnotation = type.getAnnotation(Qualifier.class);
        if (qualifierAnnotation != null) {
            beanDefinitionBuilder.qualifier(qualifierAnnotation.value());
        }

        return beanDefinitionBuilder.build();
    }

    public static BeanDefinition from(String name, Class<?> type) {
        return BeanDefinition.builder().name(name).beanClass(type).build();
    }
}
