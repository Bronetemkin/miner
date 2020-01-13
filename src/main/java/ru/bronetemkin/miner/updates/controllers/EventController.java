package ru.bronetemkin.miner.updates.controllers;

import ru.bronetemkin.miner.updates.AbstractFieldUpdate;

public interface EventController<T extends AbstractFieldUpdate> {

    void pushAll();

    void push(T t);

    void add(T t);

    void clear();

    Iterable<T> getAll();

}
