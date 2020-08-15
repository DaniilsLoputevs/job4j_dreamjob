package ru.job4j.dreamjob.store.psql;

import java.util.Collection;

/**
 * @param <I> - item Type. with that type work this Store.
 */
public interface PsqlStore<I> {

    Collection<I> findAll();

    void save(I item);

    /* USE ONLY INSIDE IMPL CLASS!!! -- use as part of method save(I item) */
    @Deprecated
    void create(I item);

    /* USE ONLY INSIDE IMPL CLASS!!! -- use as part of method save(I item) */
    @Deprecated
    void update(I item);

    I findById(int id);

    void deleteById(int id);
}
