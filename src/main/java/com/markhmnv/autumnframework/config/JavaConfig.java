package com.markhmnv.autumnframework.config;

import com.markhmnv.autumnframework.annotation.Injectable;
import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class JavaConfig implements Config {
    private final Reflections scanner;
    private final Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        this.scanner = new Reflections(packageToScan);
        this.ifc2ImplClass = ifc2ImplClass;
    }

    /**
     * Returns the implementation class for the given interface class.
     *
     * @param ifc the interface class
     * @return the implementation class for the given interface class
     * @throws RuntimeException if there are no implementation classes or more than one implementation class that has the
     *                           {@link Injectable} annotation
     */
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc)
                    .stream()
                    .filter(clazz -> clazz.isAnnotationPresent(Injectable.class))
                    .collect(Collectors.toSet());

            if(classes.size() != 1) {
                throw new RuntimeException("Unable to perform injection for field '"
                        + ifc.getName() +
                        "'. Expected exactly 1 implementation with @Injectable annotation but found " +
                        classes.size());
            }

            return classes.iterator().next();
        });
    }
}
