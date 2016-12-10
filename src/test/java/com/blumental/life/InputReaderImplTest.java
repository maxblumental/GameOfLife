package com.blumental.life;

import com.blumental.life.model.InputPOJO;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.blumental.life.TestUtils.assertGenerationIs;
import static com.blumental.life.TestUtils.sampleAliveness;
import static com.blumental.life.TestUtils.sampleInputString;
import static org.junit.Assert.assertEquals;

public class InputReaderImplTest {

    private InputReader inputReader;

    @Before
    public void setUp() throws Exception {
        inputReader = new InputReaderImpl();
    }

    @Test
    public void readInput() throws Exception {
        //given
        InputStream inputStream = new ByteArrayInputStream(
                sampleInputString().getBytes()
        );

        //when
        InputPOJO inputPOJO = inputReader.readInput(inputStream);

        //then
        assertEquals(100, inputPOJO.getStepNumber());
        assertGenerationIs(inputPOJO.getGeneration(), sampleAliveness());
    }
}