package ru.bronetemkin.miner.data;

import java.util.Random;

public class MineFieldFactory {

    public static final int FIELD_SIZE_LARGE = 34,
            FIELD_SIZE_MEDIUM = 18,
            FIELD_SIZE_SMALL = 12;

    public static MineCell[][] generateMineField(FieldSize size) {
        int fieldSize,
                minesCount;
        switch (size) {
            default: {
                fieldSize = FIELD_SIZE_SMALL;
                minesCount = 10;
                break;
            }
            case MEDIUM: {
                fieldSize = FIELD_SIZE_MEDIUM;
                minesCount = 40;
                break;
            }
            case BIG: {
                fieldSize = FIELD_SIZE_LARGE;
                minesCount = 99;
                break;
            }
        }
        MineCell[][] cells = plantMines(fieldSize, fieldSize, minesCount);
        cells = makeMinesCount(cells);
        cells = trimField(cells);
        return cells;
    }

    private static MineCell[][] fillField(int x, int y) {
        MineCell[][] cells = new MineCell[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cells[i][j] = new MineCell(0);
            }
        }

        return cells;
    }

    private static MineCell[][] plantMines(int x, int y, int minesCount) {
        MineCell[][] cells = fillField(x, y);
        Random rand = new Random();
        byte[] minesX = new byte[minesCount],
                minesY = new byte[minesCount];
        for (int i = 0; i < minesCount; i++) {
            byte mineX,
                    mineY;

            mineX = (byte) (rand.nextInt(x - 2) + 1);
            mineY = (byte) (rand.nextInt(y - 2) + 1);

            for (int j = 0; j < minesCount; j++) {
                if (i != j & minesX[j] == mineX & minesY[j] == mineY) {
                    mineX = (byte) (rand.nextInt(x - 2) + 1);
                    mineY = (byte) (rand.nextInt(y - 2) + 1);
                }
                minesX[i] = mineX;
                minesY[i] = mineY;
            }
            cells[mineX][mineY] = new MineCell(true);
        }
        return cells;
    }

    private static MineCell[][] makeMinesCount(MineCell[][] cells) {
        for (int i = 1; i < (cells.length - 1); i++) {
            for (int j = 1; j < (cells.length - 1); j++) {
                if (!cells[i][j].isMine()) {
                    int nearMinesCount = 0;
                    if (cells[i - 1][j - 1].isMine()) nearMinesCount++;
                    if (cells[i - 1][j].isMine()) nearMinesCount++;
                    if (cells[i - 1][j + 1].isMine()) nearMinesCount++;
                    if (cells[i][j + 1].isMine()) nearMinesCount++;
                    if (cells[i + 1][j + 1].isMine()) nearMinesCount++;
                    if (cells[i + 1][j].isMine()) nearMinesCount++;
                    if (cells[i + 1][j - 1].isMine()) nearMinesCount++;
                    if (cells[i][j - 1].isMine()) nearMinesCount++;
                    cells[i][j].setNearMinesCount(nearMinesCount);
                }
            }
        }
        return cells;
    }

    private static MineCell[][] trimField(MineCell[][] cells) {
        MineCell[][] result = new MineCell[cells.length - 2][cells[0].length - 2];

        for (int i = 1; i < cells.length - 1; i++) {
            System.arraycopy(cells[i], 1, result[i - 1], 0, cells[0].length - 2);
        }

        return result;
    }

}
