package com.blumental.life;

import java.io.OutputStream;
import java.io.PrintWriter;

public class GenerationImpl implements Generation {

    private boolean[][] aliveness;

    public GenerationImpl(int generationSize) {
        aliveness = new boolean[generationSize][generationSize];
    }

    @Override
    public boolean isCellAlive(int i, int j) {
        return aliveness[i][j];
    }

    @Override
    public void setCellState(int i, int j, boolean isAlive) {
        aliveness[i][j] = isAlive;
    }

    @Override
    public int size() {
        return aliveness.length;
    }

    @Override
    public Generation copy() {
        int generationSize = aliveness.length;
        GenerationImpl generation = new GenerationImpl(generationSize);
        for (int i = 0; i < generationSize; i++) {
            for (int j = 0; j < generationSize; j++) {
                generation.setCellState(i, j, aliveness[i][j]);
            }
        }
        return generation;
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (boolean[] row : aliveness) {
            writer.print("|");
            for (boolean isAlive : row) {
                writer.printf(" %d", isAlive ? 1 : 0);
            }
            writer.print(" |\n");
        }
        writer.flush();
    }
}
