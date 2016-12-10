package com.blumental.life;

import com.blumental.life.model.CellRange;
import org.junit.Test;

import java.util.List;

import static com.blumental.life.TestUtils.assertRangeIs;
import static org.junit.Assert.assertEquals;

public class LoadDistributionTest {

    @Test
    public void generationSize1() throws Exception {
        //given
        int generationSize = 1;
        int threadNumber = 4;

        //when
        List<CellRange> cellRanges = LoadDistribution.getCellRanges(generationSize, threadNumber);

        //then
        assertRangeIs(cellRanges.get(0), 0, 0, 0, 0);
    }

    @Test
    public void generationSize2() throws Exception {
        //given
        int generationSize = 2;
        int threadNumber = 4;

        //when
        List<CellRange> cellRanges = LoadDistribution.getCellRanges(generationSize, threadNumber);

        //then
        assertRangeIs(cellRanges.get(0), 0, 0, 1, 1);
    }

    @Test
    public void generationSizeIsTwiceThreadNumber() throws Exception {
        //given
        int threadNumber = 4;
        int generationSize = 8;

        //when
        List<CellRange> cellRanges = LoadDistribution.getCellRanges(generationSize, threadNumber);

        //then
        assertEquals(4, cellRanges.size());
        assertRangeIs(cellRanges.get(0), 0, 0, 1, 7);
        assertRangeIs(cellRanges.get(1), 2, 2, 3, 7);
        assertRangeIs(cellRanges.get(2), 4, 4, 5, 7);
        assertRangeIs(cellRanges.get(3), 6, 6, 7, 7);
    }
}