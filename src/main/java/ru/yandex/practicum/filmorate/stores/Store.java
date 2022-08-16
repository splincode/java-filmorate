package ru.yandex.practicum.filmorate.stores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Store<T> {
    protected final Map<Long, T> data = new HashMap();
    private long autoIncrementId;

    public abstract boolean has(T user);

    public abstract T set(T user);

    public abstract void delete(T user);

    public List<T> values() {
        return new ArrayList<>(data.values());
    }

    protected long incrementAndGetUniqueId() {
        autoIncrementId = autoIncrementId + 1;
        return autoIncrementId;
    }
}
