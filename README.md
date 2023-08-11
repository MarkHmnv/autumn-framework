# Autumn Framework
Autumn Framework is a light-weight, educational project inspired by the Spring Framework. It was created with the purpose of understanding how Spring works under the hood.

## Features
* Simple, but powerful IOC container with Singleton and Prototype scopes support
* Dependency Injection using @Inject annotation
* Component scan using @Injectable annotation with scopes support
* Inject properties from application.properties using @InjectProperty annotation
* Straightforward Java configuration
* Flexible object configurators and proxy configurators
* Generates warnings when deprecated classes are called
* Helps to understand Spring Core concepts in a simplified manner

## Getting Started

### Configuration
```java
public class JavaConfig implements Config {
...

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc)
                    .stream()
                    .filter(clazz -> clazz.isAnnotationPresent(Injectable.class))
                    .collect(Collectors.toSet());
            ...
        });
    }
}
```

### Annotations
Create Injectable classes with @Injectable and inject dependencies using @Inject.

```java
import com.markhmnv.autumnframework.core.annotation.Inject;
import lombok.RequiredArgsConstructor;

@Injectable
public class DemoService {
    @Inject
    private DemoRepository demoRepository;

    ...
}
```
Or use @RequiredArgsConstructor from Lombok
```java
import lombok.RequiredArgsConstructor;

@Injectable
@RequiredArgsConstructor
public class DemoService {
    private final DemoRepository demoRepository;

    ...
}
```
Inject properties from application.properties using @InjectProperty.
```java
@Injectable
public class DemoService {
    @InjectProperty
    private String secret;
    ...
}
```
### ApplicationContext and Startup
Create an instance of ApplicationContext via AutumnApplication.run(<package to scan>). After that, simply call context.getObject(<your class>) to get full-featured objects.
```java
public class Demo {
    public static void main(String[] args) {
        ApplicationContext context = AutumnApplication.run(Demo.class);
        DemoController demoController = context.getObject(DemoController.class);
        ...
    }
}
```