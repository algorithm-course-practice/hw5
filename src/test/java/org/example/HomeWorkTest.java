package org.example;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void check()  {

        assertEquals(-1, homeWork.calculate("1 + 2 * ( 3 - 4 )"));
        assertThrows(EmptyStackException.class, () -> homeWork.calculate("1 + 2 * ( 3 - 4 "));
        assertEquals(13, homeWork.calculate("1 + 2 * ( 10 - 4 )"));
        assertThrows(IllegalArgumentException.class, () -> homeWork.calculate(null));
        assertEquals(0.5, homeWork.calculate("sin ( 30.0 )"), 0.0001);
        assertEquals(0.5, homeWork.calculate("cos ( 60.0 )"), 0.0001);
        assertEquals(9.0, homeWork.calculate("sqr ( 3.0 )"), 0.0001);
    }

}