package ru.bronetemkin.miner.updates;

import ru.bronetemkin.miner.CellCoordinates;
import ru.bronetemkin.miner.MineCell;

public class CellUpdate {

    private CellCoordinates coordinates;

    private MineCell cell;

    public CellUpdate(CellCoordinates coordinates, MineCell cell) {
        this.coordinates = coordinates;
        this.cell = cell;
    }

    public CellCoordinates getCoordinates() {
        return coordinates;
    }

    public MineCell getCell() {
        return cell;
    }

}
