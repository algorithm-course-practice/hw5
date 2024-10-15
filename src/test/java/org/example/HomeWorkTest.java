package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeWorkTest {

    private final HomeWork homeWork = new HomeWork();

    @Test
    void check() {
        assertEquals(-1.0d, homeWork.calculate("1 + 2 * ( 3 - 4 )"));
    }

    @Test
    void checkMath() {
        assertEquals(0d, homeWork.calculate("sin(0)"));
        assertEquals(1.0d, homeWork.calculate("cos(0)"));
        assertEquals(4d, homeWork.calculate("sqr(2)"));
        assertEquals(8d, homeWork.calculate("pow(2,3)"));
    }

    @Test
    void patternCheck(){
        assertTrue(HomeWork.MATH_FUNC.matcher("sin(1)").matches());
        assertTrue(HomeWork.MATH_FUNC.matcher("cos(1)").matches());
        assertTrue(HomeWork.MATH_FUNC.matcher("pow(1)").matches());
        assertTrue(HomeWork.MATH_FUNC.matcher("sqr(1)").matches());
        assertFalse(HomeWork.MATH_FUNC.matcher("sqrt(1)").matches());
        assertFalse(HomeWork.MATH_FUNC.matcher("any(1)").matches());
        assertFalse(HomeWork.MATH_FUNC.matcher("1").matches());
        assertFalse(HomeWork.MATH_FUNC.matcher("1.0d").matches());
        assertFalse(HomeWork.MATH_FUNC.matcher("+").matches());
        assertFalse(HomeWork.MATH_FUNC.matcher(" ").matches());
    }

    @Test
    void complexCheck() {
        assertEquals(9.0d, homeWork.calculate("1 + 2 * ( 3 - 4 ) + pow(2,3) * cos(0) + sqr(2) / 2"));
    }

    @Test
    void divideCheck() {
        assertEquals(2.0d, homeWork.calculate("sqr(2) / 2"));
    }
}