package com.blumental.life;

import com.blumental.life.io.InputReaderImpl;
import com.blumental.life.model.CellRange;
import com.blumental.life.model.InputPOJO;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Launcher {

    private List<EvolutionTask> evolutionTasks;

    private int threadNumber;

    public static void main(String[] args) throws IOException {

        InputStream inputStream = Files.newInputStream(Paths.get("input.txt"));
        int threadNumber = Runtime.getRuntime().availableProcessors();

        Launcher launcher = new Launcher();

        InputPOJO inputPOJO = new InputReaderImpl().readInput(inputStream);
        Generation generation = inputPOJO.getGeneration();
        int stepNumber = inputPOJO.getStepNumber();
        launcher.setUp(generation, stepNumber, threadNumber);

        launcher.evolve();

        launcher.getResultGeneration().print(System.out);
    }

    public void setUp(Generation initialGeneration, int stepNumber, int threadNumber) {
        this.threadNumber = threadNumber;
        Generation nextGeneration = initialGeneration.copy();
        evolutionTasks = prepareEvolutionTasks(initialGeneration, nextGeneration, stepNumber);
    }

    public void evolve() {
        ExecutorService threadPool = newFixedThreadPool(threadNumber);
        evolutionTasks.forEach(threadPool::execute);
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    @Nullable
    public Generation getResultGeneration() {
        if (evolutionTasks != null && evolutionTasks.size() > 0) {
            return evolutionTasks.get(0).getCurrentGeneration();
        }
        return null;
    }

    private List<EvolutionTask> prepareEvolutionTasks(Generation generation,
                                                      Generation nextGeneration,
                                                      int stepNumber) {
        List<EvolutionTask> evolutionTasks = createEvolutionTasks(generation.size(), stepNumber);

        for (EvolutionTask task : evolutionTasks) {
            task.setPrevGeneration(generation);
            task.setNextGeneration(nextGeneration);
        }
        return evolutionTasks;
    }

    private List<EvolutionTask> createEvolutionTasks(int generationSize, int stepNumber) {
        List<EvolutionTask> evolutionTasks = new ArrayList<>();
        List<CellRange> cellRanges = LoadDistribution.getLoadDistribution(generationSize, threadNumber);
        AtomicInteger currentIteration = new AtomicInteger(0);
        AtomicInteger completeCount = new AtomicInteger(0);

        for (CellRange cellRange : cellRanges) {
            EvolutionTask task = new EvolutionTask(generationSize, cellRange, stepNumber,
                    currentIteration, completeCount, threadNumber);
            evolutionTasks.add(task);
        }

        return evolutionTasks;
    }
}
