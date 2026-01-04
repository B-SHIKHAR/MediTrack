package com.airtribe.meditrack.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Generic in-memory store backed by List & Map for quick lookup
public class DataStore<T> {
    private final List<T> list = new ArrayList<>();
    private final Map<Long, T> indexById = new HashMap<>();

    public void add(long id, T obj) {
        list.add(obj);
        indexById.put(id, obj);
    }

    public Optional<T> getById(long id) {
        return Optional.ofNullable(indexById.get(id));
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(list);
    }

    public boolean remove(long id) {
        T obj = indexById.remove(id);
        if (obj != null) {
            list.remove(obj);
            return true;
        }
        return false;
    }

    public List<T> filter(Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}
