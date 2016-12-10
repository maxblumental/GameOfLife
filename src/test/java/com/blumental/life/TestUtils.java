package com.blumental.life;

import com.blumental.life.model.CellRange;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

public class TestUtils {

    public static void assertGenerationIs(Generation generation, boolean[][] liveness) {
        assertEquals(liveness.length, generation.size());
        for (int i = 0; i < liveness.length; i++) {
            for (int j = 0; j < liveness.length; j++) {
                assertEquals(format("At i = %d, j = %d", i, j),
                        liveness[i][j], generation.isCellAlive(i, j));
            }
        }
    }

    public static void assertRangeIs(CellRange range, int i1, int j1, int i2, int j2) {
        assertEquals(i1, range.fromX());
        assertEquals(j1, range.fromY());
        assertEquals(i2, range.toX());
        assertEquals(j2, range.toY());
    }

    public static String sampleInputString() {
        return "100 4\n" +
                "0 1 0 1\n" +
                "1 0 1 0\n" +
                "0 1 0 1\n" +
                "1 0 1 0";
    }

    public static boolean[][] sampleAliveness() {
        return new boolean[][]{
                {false, true, false, true},
                {true, false, true, false},
                {false, true, false, true},
                {true, false, true, false}
        };
    }

    public static Generation sampleGeneration() {
        int generationSize = 4;
        Generation generation = new GenerationImpl(generationSize);
        boolean[][] aliveness = sampleAliveness();
        for (int i = 0; i < generationSize; i++) {
            for (int j = 0; j < generationSize; j++) {
                generation.setCellState(i, j, aliveness[i][j]);
            }
        }
        return generation;
    }
}
