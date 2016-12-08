package com.blumental.life.model;

import com.blumental.life.Generation;

public class InputPOJO {

    private final int stepNumber;

    private final Generation generation;

    public InputPOJO(int stepNumber, Generation generation) {
        this.stepNumber = stepNumber;
        this.generation = generation;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public Generation getGeneration() {
        return generation;
    }
}
