package my_files.di;

import java.util.HashMap;
import java.util.Map;

public class DI {
    
    private final Map<Class<?>, Object> instances = new HashMap<>();

    public <T> void registerSingleton(Class<T> clazz, T instance) {
        instances.put(clazz, instance);
    }

    public <T> T resolve(Class<T> clazz) {
        T instance = clazz.cast(instances.get(clazz));
        if(instance == null) {
            throw new RuntimeException("No instance registered for " + clazz.getName());
        }
        return instance;
    }
}
