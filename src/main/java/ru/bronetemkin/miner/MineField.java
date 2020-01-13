package ru.bronetemkin.miner;

import ru.bronetemkin.miner.updates.*;
import ru.bronetemkin.miner.updates.controllers.EBCtrl;
import ru.bronetemkin.miner.updates.controllers.EventController;

public class MineField extends MineFieldData {

    private EventController<AbstractFieldUpdate> ctrl = EBCtrl.get();
    private boolean restartWithoutFailIfBlowOnStartup = true;

    public MineField() {
        super();
    }

    public MineField(FieldSize size) {
        this(GAME_RESULT_UNKNOWN, MineFieldFactory.generateMineField(size), size);
    }

    public MineField(MineFieldData data) {
        this(data.getGameStatus(), data.getCells(), data.getCurFieldSize());
    }

    public MineField(int gameStatus, MineCell[][] cells, FieldSize fieldSize) {
        super(gameStatus, cells, fieldSize);
    }

    public void openCell(int mode, CellCoordinates coordinates) {
        openCell(mode, coordinates.getX(), coordinates.getY());
    }

    public void openCell(int mode, int x, int y) {
        if (getGameStatus() != GAME_RESULT_UNKNOWN) {
            generate();
            return;
        }
        if (x >= 0 && x < getSizeX()
                && y >= 0 && y < getSizeY()) {
            MineCell cell = getCell(x, y);
            switch (mode) {
                case MODE_NORMAL: {
                    if (!cell.isVisible() && !cell.hasFlag()) {
                        cell.setVisible();
                        ctrl.push(new CellFieldUpdate(makeCellUpdate(x, y)));
                        if (cell.isMine()) {
                            if (getMovesCount() < 2 && restartWithoutFailIfBlowOnStartup) {
                                setGameStatus(GAME_RESULT_UNKNOWN);
                                generate();
                                openCell(mode, x, y);
                            } else {
                                setGameStatus(GAME_RESULT_FAIL);
                                openAll();
                            }
                        } else if (cell.getNearMinesCount() == 0) {
                            ifZero(x, y);
                        }
                    }
                    break;
                }
                case MODE_FLAG_MANIPULATION: {
                    if (!cell.isVisible()) {
                        if (cell.hasFlag()) {
                            cell.setHasFlag(false);
                            ctrl.add(new CellFieldUpdate(makeCellUpdate(x, y)));
                        } else {
                            if (getFlagsCount() < getMinesCount()) {
                                cell.setHasFlag(true);
                                ctrl.add(new CellFieldUpdate(makeCellUpdate(x, y)));
                            }
                        }
                    }
                    break;
                }
            }
        }
        if (getInvisibleCellsCount() == getMinesCount()) {
            setGameStatus(GAME_RESULT_SUCCESS);
            openAll();
            ctrl.add(new GameResultUpdate(getMovesCount(), this));
        }
        incrementMovesCount();
        ctrl.pushAll();
    }

    private CellUpdate makeCellUpdate(int x, int y) {
        return new CellUpdate(new CellCoordinates(x, y), getCell(x, y));
    }

    public void cheat() {
        setGameStatus(GAME_RESULT_SUCCESS);
        openAll();

        ctrl.push(new GameResultUpdate(getMovesCount(), this));
    }

    public void update() {
        ctrl.push(new SizeFieldUpdate(getCurFieldSize()));
    }

    public void generate() {
        generate(getCurFieldSize());
    }

    public void generate(FieldSize size) {
        setCurFieldSize(size);
        setCells(MineFieldFactory.generateMineField(size));
        setGameStatus(GAME_RESULT_UNKNOWN);
        ctrl.push(new SizeFieldUpdate(getCurFieldSize()));
        CellFieldUpdateList list = new CellFieldUpdateList();
        for (int i = 0; i < getSizeX(); i++) {
            for (int j = 0; j < getSizeY(); j++) {
                list.add(new CellFieldUpdate(makeCellUpdate(i, j)));
            }
        }
        ctrl.push(list);
    }

    public int getMinesCount() {
        return countMines();
    }

    public int getFlagsCount() {
        return countFlags();
    }

    public int getInvisibleCellsCount() {
        return countInvisibleCells();
    }

    private void ifZero(int x, int y) {
        MineCell cell = getCell(x, y);

        cell.setVisible();
        if (cell.hasFlag()) cell.setHasFlag(false);
        ctrl.add(new CellFieldUpdate(makeCellUpdate(x, y)));

        if (getCell(x, y).getNearMinesCount() == 0) {
            if (checkCell(x, y - 1)) {
                ifZero(x, y - 1);
            }
            if (checkCell(x - 1, y)) {
                ifZero(x - 1, y);
            }
            if (checkCell(x, y + 1)) {
                ifZero(x, y + 1);
            }
            if (checkCell(x + 1, y)) {
                ifZero(x + 1, y);
            }
        }
    }

    private int countInvisibleCells() {
        int result = 0;
        for (MineCell[] mineCells : getCells()) {
            for (MineCell cell : mineCells) {
                if (!cell.isVisible()) result++;
            }
        }
        return result;
    }

    private int countFlags() {
        int result = 0;
        for (int i = 0; i < getSizeX(); i++) {
            for (int j = 0; j < getSizeY(); j++) {
                if (getCell(i, j).hasFlag()) result++;
            }
        }
        return result;
    }

    private int countMines() {
        int result = 0;
        for (int i = 0; i < getSizeX(); i++) {
            for (int j = 0; j < getSizeY(); j++) {
                if (getCell(i, j).isMine()) result++;
            }
        }
        return result;
    }

    private void openAll() {
        CellFieldUpdateList list = new CellFieldUpdateList();
        for (int i = 0; i < getSizeX(); i++) {
            for (int j = 0; j < getSizeY(); j++) {
                MineCell cell = getCell(i, j);
                if (!cell.isVisible()) {
                    cell.setVisible();
                    list.add(new CellFieldUpdate(makeCellUpdate(i, j)));
                }
            }
        }
    }

    public void hideAll() {
        for (int i = 0; i < getSizeX(); i++) {
            for (int j = 0; j < getSizeY(); j++) {
                MineCell cell = getCell(i, j);
                if (cell.isVisible()) cell.setVisible(false);
            }
        }
    }

    private boolean checkCell(int x, int y) {
        return (x >= 0 && x < getSizeX()
                && y >= 0 && y < getSizeY()
                && !getCell(x, y).isVisible()
                && !getCell(x, y).isMine());
    }

    public boolean isRestartWithoutFailIfBlowOnStartup() {
        return restartWithoutFailIfBlowOnStartup;
    }

    public void setRestartWithoutFailIfBlowOnStartup(boolean restartWithoutFailIfBlowOnStartup) {
        this.restartWithoutFailIfBlowOnStartup = restartWithoutFailIfBlowOnStartup;
    }
}
