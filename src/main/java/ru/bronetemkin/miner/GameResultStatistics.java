package ru.bronetemkin.miner;

public class GameResultStatistics {

    private int movesCount;
    private MineFieldData field;

    public GameResultStatistics(int movesCount, MineFieldData field) {
        this.movesCount = movesCount;
        this.field = field;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public MineFieldData getField() {
        return field;
    }

}
