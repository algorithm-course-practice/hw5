package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void check() {
        assertEquals(homeWork.calculate("1 + 2 * ( 3 - 4 )"), -1.0, 0.001);
        assertEquals(homeWork.calculate("1 + 2 * ( 3 + 4 )"), 15.0, 0.001);
        assertEquals(homeWork.calculate("1 + 2 * ( 3 * 4 )"), 25.0, 0.001);
        assertEquals(homeWork.calculate("1 + 2 * ( 10 / 2 )"), 11.0, 0.001);
        assertEquals(homeWork.calculate("1 + 2 * ( 10 ^ 2 )"), 201.0, 0.001);

        assertEquals(homeWork.calculate("sin(45) + cos(45)"), 1.3, 0.1);
        assertEquals(homeWork.calculate("sin(45) * cos(45)"), 0.4, 0.1);
    }

}