package ru.bronetemkin.miner.updates;

import ru.bronetemkin.miner.FieldSize;

public class SizeFieldUpdate extends AbstractFieldUpdate<FieldSize> {
    public SizeFieldUpdate(FieldSize size) {
        super(AbstractFieldUpdate.UPDATE_FIELD_SIZE);
        setUpdateContent(size);
    }
}
