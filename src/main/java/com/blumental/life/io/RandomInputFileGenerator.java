package com.blumental.life.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static java.nio.file.Files.newOutputStream;

public class RandomInputFileGenerator {

    private static final int GENERATION_SIZE = 1_000;
    public static final String RANDOM_INPUT_FILENAME = "random_input.txt";

    public static void main(String[] args) {
        RandomInputFileGenerator generator = new RandomInputFileGenerator();
        generator.generate(GENERATION_SIZE, Paths.get(RANDOM_INPUT_FILENAME));
    }

    public void generate(int generationSize, Path outputFile) {
        Random random = new Random();
        try (PrintWriter writer = new PrintWriter(newOutputStream(outputFile))) {

            writer.printf("1 %d\n", generationSize);

            for (int i = 0; i < generationSize; i++) {
                for (int j = 0; j < generationSize; j++) {
                    writer.printf("%d ", random.nextInt(2));
                }
                writer.print("\n");
            }
        } catch (IOException e) {
            System.err.println("An error during generation: " + e.toString());
        }
    }
}
