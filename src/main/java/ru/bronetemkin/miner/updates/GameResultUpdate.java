package ru.bronetemkin.miner.updates;

import ru.bronetemkin.miner.MineFieldData;

public class GameResultUpdate extends AbstractFieldUpdate<MineFieldData> {

    private int movesCount;

    public GameResultUpdate(int movesCount, MineFieldData field) {
        super(UPDATE_CELLS);
        this.movesCount = movesCount;
        setUpdateContent(field);
    }

    public int getMovesCount() {
        return movesCount;
    }
}
