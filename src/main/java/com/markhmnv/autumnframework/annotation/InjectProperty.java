package com.markhmnv.autumnframework.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation used to mark a field as injectable with a property value.
 * The field marked with this annotation will be populated with the value of the specified property.
 * If no value is provided, the name of the field will be used as the property key.
 */
@Retention(RUNTIME)
public @interface InjectProperty {
    String value() default "";
}
