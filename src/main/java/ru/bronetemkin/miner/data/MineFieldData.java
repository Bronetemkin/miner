package ru.bronetemkin.miner.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MineFieldData {

    public static final int MODE_NORMAL = 1,
            MODE_FLAG_MANIPULATION = 2;

    public static final int GAME_RESULT_FAIL = 2,
            GAME_RESULT_SUCCESS = 1,
            GAME_RESULT_UNKNOWN = -1;

    private int gameStatus = GAME_RESULT_UNKNOWN;
    private FieldSize curFieldSize;
    private MineCell[][] cells;
    private List<PlayerMove> playerMovesHistory;

    public MineFieldData() {
        this.gameStatus = GAME_RESULT_UNKNOWN;
        this.cells = MineFieldFactory.generateMineField(FieldSize.SMALL);
        this.curFieldSize = FieldSize.SMALL;
        this.playerMovesHistory = new ArrayList<>();
    }

    public MineFieldData(int gameStatus, MineCell[][] cells, FieldSize fieldSize) {
        this.gameStatus = gameStatus;
        this.cells = cells;
        this.curFieldSize = fieldSize;
        if (playerMovesHistory == null) playerMovesHistory = new ArrayList<>();
    }

    public MineFieldData(int gameStatus, MineCell[][] cells, FieldSize fieldSize, List<PlayerMove> playerMovesHistory) {
        this(gameStatus, cells, fieldSize);
        this.playerMovesHistory = playerMovesHistory;
    }

    public FieldSize getCurFieldSize() {
        return curFieldSize;
    }

    protected void setCurFieldSize(FieldSize curFieldSize) {
        this.curFieldSize = curFieldSize;
    }

    public void addPlayerMoves(Collection<PlayerMove> updates) {
        log("add cell playerMovesHistory (" + updates.size() + ") [" + playerMovesHistory.size() + "]");
        this.playerMovesHistory.addAll(updates);
    }

    public void addPlayerMove(PlayerMove move) {
        log("add cell playerMovesHistory (" + playerMovesHistory.size() + ")");
        this.playerMovesHistory.add(move);
    }

    protected void clearPlayerMovesHistory() {
        this.playerMovesHistory.clear();
    }

    public List<PlayerMove> getPlayerMovesHistory() {
        log("getPlayerMovesHistory() " + playerMovesHistory.size());
        return playerMovesHistory;
    }

    public void log(String msg) {

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
