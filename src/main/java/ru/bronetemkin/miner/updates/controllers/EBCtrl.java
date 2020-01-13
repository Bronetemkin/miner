package ru.bronetemkin.miner.updates.controllers;

import org.greenrobot.eventbus.EventBus;
import ru.bronetemkin.miner.updates.AbstractFieldUpdate;

import java.util.ArrayList;
import java.util.List;

public class EBCtrl<T extends AbstractFieldUpdate> implements EventController<T> {

    private static EventController<AbstractFieldUpdate> instance;
    private List<T> updates;

    private EBCtrl() {
        updates = new ArrayList<>();
    }

    public static EventController<AbstractFieldUpdate> get() {
        if (instance == null) instance = new EBCtrl<>();
        return instance;
    }

    @Override
    public void pushAll() {
        EventBus.getDefault().postSticky(updates);
    }

    @Override
    public void push(T t) {
        EventBus.getDefault().postSticky(t);
    }

    @Override
    public void add(T t) {
        updates.add(t);
    }

    @Override
    public void clear() {
        updates.clear();
    }

    @Override
    public Iterable<T> getAll() {
        return updates;
    }
}
