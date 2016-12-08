package com.blumental.life;

import java.io.OutputStream;

public interface Generation {

    boolean isCellAlive(int i, int j);

    void setCellState(int i, int j, boolean isAlive);

    int size();

    Generation copy();

    void print(OutputStream outputStream);
}
