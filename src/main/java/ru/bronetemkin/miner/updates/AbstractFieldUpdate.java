package ru.bronetemkin.miner.updates;

import ru.bronetemkin.miner.MineField;

public class AbstractFieldUpdate<T> {

    public static final int UPDATE_FIELD_SIZE = 1,
            UPDATE_CELLS = 2,
            UPDATE_PLAYER_MOVES = 3;

    private int updateStatus;
    private int gameStatus;
    private T updateContent;

    public AbstractFieldUpdate(int updateStatus) {
        this.updateStatus = updateStatus;
        gameStatus = MineField.GAME_RESULT_UNKNOWN;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateContent(T content) {
        this.updateContent = content;
    }

    public T getUpdateContent() {
        return updateContent;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

}
