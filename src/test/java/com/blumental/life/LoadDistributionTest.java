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
        List<CellRange> cellRanges = LoadDistribution.getLoadDistribution(generationSize, threadNumber);

        //then
        assertRangeIs(cellRanges.get(0), 0, 0, 0, 0);
    }

    @Test
    public void generationSize2() throws Exception {
        //given
        int generationSize = 2;
        int threadNumber = 4;

        //when
        List<CellRange> cellRanges = LoadDistribution.getLoadDistribution(generationSize, threadNumber);

        //then
        assertRangeIs(cellRanges.get(0), 0, 0, 1, 1);
    }

    @Test
    public void generationSizeIsTwiceThreadNumber() throws Exception {
        //given
        int threadNumber = 4;
        int generationSize = 8;

        //when
        List<CellRange> cellRanges = LoadDistribution.getLoadDistribution(generationSize, threadNumber);

        //then
        assertEquals(4, cellRanges.size());
        assertRangeIs(cellRanges.get(0), 0, 0, 7, 1);
        assertRangeIs(cellRanges.get(1), 0, 2, 7, 3);
        assertRangeIs(cellRanges.get(2), 0, 4, 7, 5);
        assertRangeIs(cellRanges.get(3), 0, 6, 7, 7);
    }
}