package com.blumental.life;

import java.util.concurrent.Callable;

public class EvolutionTask implements Callable<Void> {

    private final int generationSize;

    private final Point fromPoint;
    private final Point toPoint;

    private Generation prevGeneration;
    private Generation nextGeneration;

    public EvolutionTask(int generationSize, Point fromPoint, Point toPoint) {
        this.generationSize = generationSize;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
    }

    @Override
    public Void call() throws Exception {
        for (int i = fromPoint.getX(); i < toPoint.getX(); i++) {
            for (int j = fromPoint.getY(); j < toPoint.getY(); j++) {
                updateCell(i, j);
            }
        }
        Generation tmp = prevGeneration;
        prevGeneration = nextGeneration;
        nextGeneration = tmp;
        return null;
    }

    private void updateCell(int i, int j) {
        int generationSize = nextGeneration.size();
        int liveNeighboursCount = 0;
        boolean isAlive = prevGeneration.isCellAlive(i, j);
        for (int k = i - 1; k < i + 2; k++) {
            for (int l = j - 1; l < j + 2; l++) {
                if (k == i && l == j) continue;
                liveNeighboursCount += tryNeighbour(k, l);
            }
        }

        if (isAlive) {
            if (liveNeighboursCount != 2 && liveNeighboursCount != 3) {
                nextGeneration.setCellState(i, j, false);
            }
        } else {
            if (liveNeighboursCount == 3) {
                nextGeneration.setCellState(i, j, true);
            }
        }
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

    public boolean isValidCell(int i, int j) {
        return isValidCoordinate(i) && isValidCoordinate(j);
    }

    private boolean isValidCoordinate(int i) {
        return i >= 0 && i < generationSize;
    }
}
