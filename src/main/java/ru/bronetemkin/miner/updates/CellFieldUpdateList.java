package ru.bronetemkin.miner.updates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CellFieldUpdateList extends AbstractFieldUpdate<List<CellFieldUpdate>> {

    public CellFieldUpdateList(CellFieldUpdate... updates) {
        super(UPDATE_CELLS);
        setUpdateContent(new ArrayList<>());
        add(updates);
    }

    public void add(CellFieldUpdate... updates) {
        getUpdateContent().addAll(Arrays.asList(updates));
    }

}
