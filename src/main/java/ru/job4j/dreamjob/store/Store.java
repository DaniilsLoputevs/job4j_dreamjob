package ru.job4j.dreamjob.store;

import java.util.Collection;

/**
 * @param <I> - item Type. with that type work this Store.
 */
public interface Store<I> {

    Collection<I> getAll();

    void save(I item);

    I getById(int id);

    void deleteById(int id);
}
