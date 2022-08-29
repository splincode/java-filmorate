package ru.yandex.practicum.filmorate.builders;

import java.util.HashMap;
import java.util.Map;

public class AutoIncrementBuilder {
    private static final Map<String, Long> models = new HashMap();

    public static Long autoincrement(Class<?> className) {
        Long autoincrementId = 1L;

        if (models.containsKey(className.getName())) {
            autoincrementId = models.get(className.getName()) + autoincrementId;
        }

        models.put(className.getName(), autoincrementId);

        return autoincrementId;
    }
}
