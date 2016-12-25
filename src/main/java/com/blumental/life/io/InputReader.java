package com.blumental.life.io;

import com.blumental.life.model.InputPOJO;

import java.io.InputStream;

public interface InputReader {

    InputPOJO readInput(InputStream inputStream);
}
