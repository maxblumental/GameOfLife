package com.blumental.life.model;

public class CellRange {

    private final Cell fromCell;

    private final Cell toCell;

    public CellRange(Cell fromCell, Cell toCell) {
        this.fromCell = fromCell;
        this.toCell = toCell;
    }

    public int fromX() {
        return fromCell.getX();
    }

    public int fromY() {
        return fromCell.getY();
    }

    public int toX() {
        return toCell.getX();
    }

    public int toY() {
        return toCell.getY();
    }

}