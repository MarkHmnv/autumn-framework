package com.markhmnv.autumnframework.annotation;

import com.markhmnv.autumnframework.enums.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark a class as injectable.
 * The class annotated with this annotation can be injected by the dependency injection framework.
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Injectable {
    Scope scope() default Scope.SINGLETON;
}
