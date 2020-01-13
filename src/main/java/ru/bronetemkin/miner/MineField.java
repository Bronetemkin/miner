package ru.bronetemkin.miner.data;

import ru.bronetemkin.miner.data.updates.AbstractFieldUpdate;
import ru.bronetemkin.miner.data.updates.CellFieldUpdate;
import ru.bronetemkin.miner.data.updates.CellUpdate;
import ru.bronetemkin.miner.data.updates.SizeFieldUpdate;
import ru.bronetemkin.miner.data.updates.UpdateListener;

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
        addPlayerMoves(data.getPlayerMovesHistory());
    }

    public MineField(int gameStatus, MineCell[][] cells, FieldSize fieldSize) {
        super(gameStatus, cells, fieldSize);
        cellFieldUpdate = new CellFieldUpdate();
    }

    public void setMineFieldData(MineFieldData data) {
        setGameStatus(data.getGameStatus());
        setCells(data.getCells());
        setCurFieldSize(data.getCurFieldSize());
    }

    public void setUpdateListener(UpdateListener<AbstractFieldUpdate> updateListener) {
        this.updateListener = updateListener;
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
            addPlayerMove(new PlayerMove(mode, new CellCoordinates(x, y)));
            MineCell cell = getCell(x, y);
            switch (mode) {
                case MODE_NORMAL: {
                    if (!cell.isVisible() && !cell.hasFlag()) {
                        cell.setVisible();
                        cellFieldUpdate.addUpdate(makeCellUpdate(x, y));
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
                            cellFieldUpdate.addUpdate(makeCellUpdate(x, y));
                        } else {
                            if (getFlagsCount() < getMinesCount()) {
                                cell.setHasFlag(true);
                                cellFieldUpdate.addUpdate(makeCellUpdate(x, y));
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
        }
        finishUpdate(getGameStatus());
    }

    private CellUpdate makeCellUpdate(CellCoordinates coordinates) {
        return makeCellUpdate(coordinates.getX(), coordinates.getY());
    }

    private CellUpdate makeCellUpdate(int x, int y) {
        return new CellUpdate(new CellCoordinates(x, y), getCell(x, y));
    }

    public void cheat() {
        setGameStatus(GAME_RESULT_SUCCESS);
        openAll();
        finishUpdate(GAME_RESULT_SUCCESS);
    }

    public void update() {
        log("update");
        updateListener.onUpdate(new SizeFieldUpdate(getSizeX(), getSizeY()));
//        for (PlayerMove move : getPlayerMovesHistory()) {
//            openCell(move.getMode(), move.getCoordinates());
//            cellFieldUpdate.addUpdate(makeCellUpdate(move.getCoordinates()));
//        }
        updateListener.onUpdate(cellFieldUpdate);
//        updateListener.onUpdate(new PlayerMovementUpdate(getGameStatus(), getPlayerMovesHistory()));
    }

    public void generate() {
        generate(getCurFieldSize());
    }

    public void generate(FieldSize size) {
        setCurFieldSize(size);
        setCells(MineFieldFactory.generateMineField(size));
        setGameStatus(GAME_RESULT_UNKNOWN);
        clearPlayerMovesHistory();
        updateListener.onUpdate(new SizeFieldUpdate(getSizeX(), getSizeY()));
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
