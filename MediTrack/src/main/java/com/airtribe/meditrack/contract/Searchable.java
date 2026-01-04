package com.airtribe.meditrack.contract;

import java.util.List;

public interface Searchable<T> {
    List<T> searchByName(String name);
    default List<T> searchByPartial(String token) {
        return searchByName(token);
    }
}
