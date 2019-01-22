package ru.bronetemkin.miner.data.updates;


import java.util.List;

import ru.bronetemkin.miner.data.PlayerMove;

public class PlayerMovementUpdate extends AbstractFieldUpdate<List<PlayerMove>> {

    public PlayerMovementUpdate(int gameStatus) {
        super(AbstractFieldUpdate.UPDATE_PLAYER_MOVES);
        setGameStatus(gameStatus);
    }

    public PlayerMovementUpdate(int gameStatus, List<PlayerMove> playerMoves) {
        this(gameStatus);
        setUpdateContent(playerMoves);
    }

}
