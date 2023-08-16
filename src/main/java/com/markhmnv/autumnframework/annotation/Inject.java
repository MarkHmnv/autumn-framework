package com.markhmnv.autumnframework.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation is used to mark fields, methods or constructors that need to be injected by the dependency injection framework.
 *
 * <p>
 * The {@code Inject} annotation is a meta-annotation that indicates the target element can be injected with a value provided by the dependency injection framework during runtime.
 * The value being injected may be dependencies, configurations, services, or other bean instances managed by the DI container.
 * </p>
 *
 */
@Retention(RUNTIME)
public @interface Inject {
}
