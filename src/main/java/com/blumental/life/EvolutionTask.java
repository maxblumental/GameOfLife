package com.blumental.life;

import com.blumental.life.model.CellRange;

import java.util.concurrent.atomic.AtomicInteger;

public class EvolutionTask implements Runnable {

    private final int generationSize;
    private final CellRange cellRange;
    private final int stepNumber;
    private final int threadNumber;
    private final AtomicInteger currentIteration;
    private final AtomicInteger completeCount;
    private Generation prevGeneration;
    private Generation nextGeneration;

    public EvolutionTask(int generationSize, CellRange cellRange, int stepNumber,
                         AtomicInteger currentIteration, AtomicInteger completeCount, int threadNumber) {
        this.generationSize = generationSize;
        this.cellRange = cellRange;
        this.stepNumber = stepNumber;
        this.threadNumber = threadNumber;
        this.currentIteration = currentIteration;
        this.completeCount = completeCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < stepNumber; i++) {
            if (currentIteration.get() != i) {
                synchronized (EvolutionTask.class) {
                    try {
                        while (currentIteration.get() != i) {
                            EvolutionTask.class.wait();
                        }
                    } catch (InterruptedException e) {
                        System.err.println("An error during sleep!");
                    }
                }
            }
            makeIteration();
            synchronized (EvolutionTask.class) {
                int count = completeCount.incrementAndGet();
                if (count == threadNumber) {
                    currentIteration.incrementAndGet();
                    completeCount.set(0);
                    EvolutionTask.class.notifyAll();
                }
            }
        }
    }

    public Generation getCurrentGeneration() {
        return prevGeneration;
    }

    private void makeIteration() {
        for (int i = cellRange.fromX(); i <= cellRange.toX(); i++) {
            for (int j = cellRange.fromY(); j <= cellRange.toY(); j++) {
                updateCell(i, j);
            }
        }

        Generation generation = prevGeneration;
        prevGeneration = nextGeneration;
        nextGeneration = generation;
    }

    private void updateCell(int i, int j) {
        boolean isAlive = prevGeneration.isCellAlive(i, j);
        int aliveNeighboursCount = getAliveNeighboursCount(i, j);

        if (isAlive) {
            boolean isStillAlive = aliveNeighboursCount == 2 || aliveNeighboursCount == 3;
            nextGeneration.setCellState(i, j, isStillAlive);
        } else {
            boolean canBeReproduced = aliveNeighboursCount == 3;
            nextGeneration.setCellState(i, j, canBeReproduced);
        }
    }

    private int getAliveNeighboursCount(int i, int j) {
        int liveNeighboursCount = 0;
        for (int k = i - 1; k < i + 2; k++) {
            for (int l = j - 1; l < j + 2; l++) {
                if (k == i && l == j) continue;
                liveNeighboursCount += tryNeighbour(k, l);
            }
        }
        return liveNeighboursCount;
    }

    private int tryNeighbour(int i, int j) {
        if (isValidCell(i, j) && prevGeneration.isCellAlive(i, j)) {
            return 1;
        }
        return 0;
    }

    public void setPrevGeneration(Generation prevGeneration) {
        this.prevGeneration = prevGeneration;
    }

    public void setNextGeneration(Generation nextGeneration) {
        this.nextGeneration = nextGeneration;
    }

    private boolean isValidCell(int i, int j) {
        return isValidCoordinate(i) && isValidCoordinate(j);
    }

    private boolean isValidCoordinate(int i) {
        return i >= 0 && i < generationSize;
    }
}
