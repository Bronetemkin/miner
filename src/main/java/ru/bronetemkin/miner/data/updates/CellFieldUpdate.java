package ru.bronetemkin.miner.data.updates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.bronetemkin.miner.data.MineField;

public class CellFieldUpdate extends AbstractFieldUpdate<List<CellUpdate>> {

    public CellFieldUpdate() {
        this(MineField.GAME_RESULT_UNKNOWN);
    }

    public CellFieldUpdate(int gameStatus) {
        super(AbstractFieldUpdate.UPDATE_CELLS);
        setUpdateContent(new ArrayList<CellUpdate>());
        setGameStatus(gameStatus);
    }

    public void addUpdates(Collection<CellUpdate> updates) {
        getUpdateContent().addAll(updates);
    }

    public void addUpdate(CellUpdate update) {
        getUpdateContent().add(update);
    }

    public void clear() {
        setGameStatus(MineField.GAME_RESULT_UNKNOWN);
        getUpdateContent().clear();
    }
}
