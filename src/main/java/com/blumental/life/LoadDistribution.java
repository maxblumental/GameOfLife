package com.blumental.life;

import com.blumental.life.model.Cell;
import com.blumental.life.model.CellRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadDistribution {

    public static List<CellRange> getLoadDistribution(int generationSize, int threadNumber) {
        if (generationSize < 2 * threadNumber) {
            return getSingleRange(generationSize);
        }

        return getSplitRanges(generationSize, threadNumber);
    }

    private static List<CellRange> getSingleRange(int generationSize) {
        Cell fromCell = new Cell(0, 0);
        Cell toCell = new Cell(generationSize - 1, generationSize - 1);
        CellRange cellRange = new CellRange(fromCell, toCell);
        return Collections.singletonList(cellRange);
    }

    private static List<CellRange> getSplitRanges(int generationSize, int threadNumber) {
        int shareWidth = generationSize / threadNumber;
        List<CellRange> cellRanges = new ArrayList<>();
        for (int i = 0; i < threadNumber; i++) {
            Cell fromCell = new Cell(0, i * shareWidth);
            Cell toCell = new Cell(generationSize - 1, (i + 1) * shareWidth - 1);
            CellRange cellRange = new CellRange(fromCell, toCell);
            cellRanges.add(cellRange);
        }
        return cellRanges;
    }
}
