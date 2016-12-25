package com.blumental.life.benchmarking;

import com.blumental.life.Launcher;
import com.blumental.life.io.InputReader;
import com.blumental.life.io.InputReaderImpl;
import com.blumental.life.model.InputPOJO;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static com.blumental.life.io.RandomInputFileGenerator.RANDOM_INPUT_FILENAME;

@State(Scope.Benchmark)
public class GameOfLifeBenchmark {

    private Launcher launcher;
    private InputPOJO inputPOJO;

    @Param({"1", "2", "4"})
    private int threadNumber;

    @Setup(Level.Trial)
    public void setUpTrial() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get(RANDOM_INPUT_FILENAME));
        InputReader inputReader = new InputReaderImpl();
        inputPOJO = inputReader.readInput(inputStream);
    }

    @Setup(Level.Invocation)
    public void setUpInvocation() throws IOException {
        launcher = new Launcher();
        launcher.setUp(inputPOJO.getGeneration(), inputPOJO.getStepNumber(), threadNumber);
    }

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Timeout(time = 100, timeUnit = TimeUnit.MINUTES)
    @Warmup(iterations = 3)
    @Measurement(iterations = 10)
    @Fork(1)
    public void measureGameOfLife() {
        launcher.evolve();
    }
}
