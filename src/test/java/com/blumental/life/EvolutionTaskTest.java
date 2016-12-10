package com.blumental.life;

import com.blumental.life.model.Cell;
import com.blumental.life.model.CellRange;
import org.junit.Before;
import org.junit.Test;

import static com.blumental.life.TestUtils.assertGenerationIs;
import static com.blumental.life.TestUtils.sampleGeneration;

public class EvolutionTaskTest {

    private EvolutionTask task;

    @Before
    public void setUp() throws Exception {
        Cell from = new Cell(0, 0);
        Cell to = new Cell(3, 3);
        CellRange cellRange = new CellRange(from, to);
        task = new EvolutionTask(4, cellRange);
    }

    @Test
    public void call() throws Exception {
        //given
        Generation generation = sampleGeneration();
        Generation nextGeneratoin = generation.copy();
        task.setPrevGeneration(generation);
        task.setNextGeneration(nextGeneratoin);

        //when
        task.call();

        //then
        boolean[][] afterOneStep = {
                {false, true, true, false},
                {true, false, false, true},
                {true, false, false, true},
                {false, true, true, false}
        };

        assertGenerationIs(nextGeneratoin, afterOneStep);
    }
}