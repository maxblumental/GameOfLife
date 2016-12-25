package com.blumental.life;

import com.blumental.life.model.Cell;
import com.blumental.life.model.CellRange;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.blumental.life.TestUtils.assertGenerationIs;
import static com.blumental.life.TestUtils.sampleGeneration;

public class EvolutionTaskTest {

    private EvolutionTask task;

    @Before
    public void setUp() throws Exception {
        int generationSize = 4;

        Cell from = new Cell(0, 0);
        Cell to = new Cell(generationSize - 1, generationSize - 1);
        CellRange cellRange = new CellRange(from, to);

        int stepNumber = 1;

        AtomicInteger currentIteration = new AtomicInteger(0);
        AtomicInteger completeCount = new AtomicInteger(0);

        int threadNumber = 1;

        task = new EvolutionTask(
                generationSize, cellRange, stepNumber,
                currentIteration, completeCount,
                threadNumber
        );
    }

    @Test
    public void evolveOneStep() throws Exception {
        //given
        Generation generation = sampleGeneration();
        Generation nextGeneratoin = generation.copy();
        task.setPrevGeneration(generation);
        task.setNextGeneration(nextGeneratoin);

        //when
        task.run();

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