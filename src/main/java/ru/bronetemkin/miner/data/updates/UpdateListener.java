package ru.bronetemkin.miner.data.updates;

public interface UpdateListener<T> {
    void onUpdate(T t);
}
