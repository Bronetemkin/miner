package ru.bronetemkin.miner;

public class MineCell {

    private boolean isMine = false,
            isVisible = false,
            hasFlag = false;
    private int nearMinesCount;

    public MineCell(boolean isMine) {
        this.isMine = isMine;
        nearMinesCount = 0;
    }

    public MineCell(int nearMinesCount) {
        this.nearMinesCount = nearMinesCount;
    }

    public void setVisible() {
        this.isVisible = true;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setNearMinesCount(int nearMinesCount) {
        this.nearMinesCount = nearMinesCount;
    }

    public int getNearMinesCount() {
        return nearMinesCount;
    }

}
