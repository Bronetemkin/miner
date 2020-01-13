package ru.bronetemkin.miner.updates;

public class CellFieldUpdate extends AbstractFieldUpdate<CellUpdate> {

    public CellFieldUpdate(CellUpdate cellUpdate) {
        super(AbstractFieldUpdate.UPDATE_CELLS);
        setUpdateContent(cellUpdate);
    }
}
