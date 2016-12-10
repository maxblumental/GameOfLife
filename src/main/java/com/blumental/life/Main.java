package com.blumental.life;

import com.blumental.life.model.CellRange;
import com.blumental.life.model.InputPOJO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("input.txt");
        run(Files.newInputStream(path), System.out);
    }

    private static void run(InputStream inputStream, OutputStream outputStream) throws IOException {
        InputReaderImpl inputReader = new InputReaderImpl();
        InputPOJO inputPOJO = inputReader.readInput(inputStream);

        Generation generation = inputPOJO.getGeneration();
        Generation nextGeneration = generation.copy();

        List<EvolutionTask> evolutionTasks = createEvolutionTasks(generation.size());

        for (EvolutionTask task : evolutionTasks) {
            task.setPrevGeneration(generation);
            task.setNextGeneration(nextGeneration);
        }

        int stepNumber = inputPOJO.getStepNumber();
        ExecutorService threadPool = newFixedThreadPool(getThreadNumber());
        for (int i = 0; i < stepNumber; i++) {
            try {
                threadPool.invokeAll(evolutionTasks);
            } catch (InterruptedException ignored) {
            }
        }

        threadPool.shutdown();

        Generation finalGeneration = evolutionTasks.get(0).getFinalGeneration();
        finalGeneration.print(outputStream);
    }

    private static int getThreadNumber() {
        return getRuntime().availableProcessors();
    }

    private static List<EvolutionTask> createEvolutionTasks(int generationSize) {
        List<EvolutionTask> evolutionTasks = new ArrayList<>();
        List<CellRange> cellRanges = LoadDistribution.getCellRanges(generationSize, getThreadNumber());

        for (CellRange cellRange : cellRanges) {
            EvolutionTask task = new EvolutionTask(generationSize, cellRange);
            evolutionTasks.add(task);
        }

        return evolutionTasks;
    }
}
