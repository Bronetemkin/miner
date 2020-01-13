package ru.bronetemkin.miner;

public class PlayerMove {

    private int mode;
    private CellCoordinates coordinates;

    public PlayerMove(int mode, CellCoordinates coordinates) {
        this.mode = mode;
        this.coordinates = coordinates;
    }

    public int getMode() {
        return mode;
    }

    public CellCoordinates getCoordinates() {
        return coordinates;
    }
}
