package ru.bronetemkin.miner;

public enum FieldSize {
    BIG(32,32, 99),
    MEDIUM(16,16, 40),
    SMALL(10,10, 10);

    private int sizeX, sizeY, minesCount;

    FieldSize(int sizeX, int sizeY, int minesCount) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.minesCount = minesCount;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getMinesCount() {
        return minesCount;
    }
}
