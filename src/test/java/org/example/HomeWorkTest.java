package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();


    @Test
    void check() {
        assertEquals(-1.0, homeWork.calculate("1 + 2 * ( 3 - 4 )"), 0.0001);
        assertEquals(25.0, homeWork.calculate("sqr(5)"), 0.0001);
        assertEquals(8, homeWork.calculate("pow(2,3)"), 0.0001);
    }

}