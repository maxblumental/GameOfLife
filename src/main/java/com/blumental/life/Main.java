package com.blumental.life;

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

    private static void run(InputStream inputStream, OutputStream outputStream) {
        InputReaderImpl inputReader = new InputReaderImpl();
        InputPOJO inputPOJO = inputReader.readInput(inputStream);

        Generation prevGeneration = inputPOJO.getGeneration();
        Generation nextGeneration = prevGeneration.copy();

        int threadNumber = getRuntime().availableProcessors();
        List<EvolutionTask> evolutionTasks = createEvolutionTasks(prevGeneration.size(), threadNumber);

        for (EvolutionTask task : evolutionTasks) {
            task.setPrevGeneration(prevGeneration);
            task.setNextGeneration(nextGeneration);
        }

        int stepNumber = inputPOJO.getStepNumber();
        ExecutorService threadPool = newFixedThreadPool(threadNumber);
        for (int i = 0; i < stepNumber; i++) {
            try {
                threadPool.invokeAll(evolutionTasks);
            } catch (InterruptedException ignored) {
            }
        }

        threadPool.shutdown();

        prevGeneration.print(outputStream);
        nextGeneration.print(outputStream);
    }

    private static List<EvolutionTask> createEvolutionTasks(int generationSize, int threadNumber) {
        List<EvolutionTask> evolutionTasks = new ArrayList<>();
        int shareWidth = generationSize / threadNumber;

        for (int i = 0; i < threadNumber; i++) {
            int fromX = shareWidth * i;
            int toX = fromX + shareWidth;

            Point fromPoint = new Point(fromX, 0);
            Point toPoint = new Point(toX, generationSize - 1);

            EvolutionTask runnable = new EvolutionTask(generationSize, fromPoint, toPoint);
            evolutionTasks.add(runnable);
        }

        return evolutionTasks;
    }
}
