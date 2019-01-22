package ru.bronetemkin.miner.data.updates;

import ru.bronetemkin.miner.data.CellCoordinates;

public class SizeFieldUpdate extends AbstractFieldUpdate<CellCoordinates> {


    private int x,
            y;

    public SizeFieldUpdate(int x, int y) {
        super(AbstractFieldUpdate.UPDATE_FIELD_SIZE);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
