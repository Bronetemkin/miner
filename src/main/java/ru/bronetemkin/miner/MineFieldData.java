package ru.bronetemkin.miner;

public class MineFieldData {

    public static final int MODE_NORMAL = 1,
            MODE_FLAG_MANIPULATION = 2;

    public static final int GAME_RESULT_FAIL = 2,
            GAME_RESULT_SUCCESS = 1,
            GAME_RESULT_UNKNOWN = -1;

    private int movesCount;
    private int gameStatus;
    private FieldSize curFieldSize;
    private MineCell[][] cells;

    public MineFieldData() {
        this(GAME_RESULT_UNKNOWN, MineFieldFactory.generateMineField(FieldSize.SMALL), FieldSize.SMALL);
    }

    public MineFieldData(int gameStatus, MineCell[][] cells, FieldSize fieldSize) {
        this.gameStatus = gameStatus;
        this.cells = cells;
        this.curFieldSize = fieldSize;
    }

    public void incrementMovesCount() {
        movesCount++;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public FieldSize getCurFieldSize() {
        return curFieldSize;
    }

    protected void setCurFieldSize(FieldSize curFieldSize) {
        this.curFieldSize = curFieldSize;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    protected void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public MineCell[][] getCells() {
        return cells;
    }

    protected void setCells(MineCell[][] cells) {
        this.cells = cells;
    }

    public MineCell getCell(int x, int y) {
        return cells[x][y];
    }

    public MineCell getCell(CellCoordinates coordinates) {
        return cells[coordinates.getX()][coordinates.getY()];
    }

    public int getSizeX() {
        return cells.length;
    }

    public int getSizeY() {
        return cells[0].length;
    }
}
