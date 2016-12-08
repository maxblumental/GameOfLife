package com.blumental.life;

import com.blumental.life.model.InputPOJO;

import java.io.InputStream;
import java.util.Scanner;

public class InputReaderImpl implements InputReader {

    @Override
    public InputPOJO readInput(InputStream inputStream) {
        int stepNumber;
        GenerationImpl generation;
        try (Scanner scanner = new Scanner(inputStream)) {
            stepNumber = scanner.nextInt();
            int generationSize = scanner.nextInt();
            generation = new GenerationImpl(generationSize);
            for (int i = 0; i < generationSize; i++) {
                for (int j = 0; j < generationSize; j++) {
                    int alivenessNumber = scanner.nextInt();
                    boolean isAlive = alivenessNumber == 1;
                    generation.setCellState(i, j, isAlive);
                }
            }
        }

        return new InputPOJO(stepNumber, generation);
    }
}
