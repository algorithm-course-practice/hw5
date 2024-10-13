package org.example;

import org.junit.jupiter.api.Test;

import static org.example.HomeWork.translate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void check() {
        assertEquals("2 3 4 * +", translate("2 + 3 * 4"));
        assertEquals("1 2 + 3 -", translate("1 + 2 - 3"));
        assertEquals("2 3 + 4 *", translate("( ( 2 + 3 ) ) * 4"));
        assertEquals("2 3 4 * + 5 - 7 6 * 3 / + 2 3 2 * - 5 2 - 2 * +", translate("2 + 3 * 4 - 5 + 7 * 6 / 3 - 2 * 3 2 + ( 5 - 2 ) * 2"));
        assertThrows(IllegalArgumentException.class,() -> translate("( 2 + 3 ) ) * 4"));
        assertThrows(IllegalArgumentException.class,() -> translate("( 2 + 3 d ) ) * 4"));
        assertEquals("1 2 3 * -", translate("1 - 2 * 3"));
        assertEquals("1 2 - 20 sin +", translate("1 - 2 + sin ( 20 )"));
        assertEquals("3 1 2 - * 10 sin *", translate("3 * ( 1 - 2 ) * sin ( 10 )"));
        assertEquals("4 1 2 * 3 - * 11 cos *", translate("4 * ( 1 * 2 - 3 ) * cos ( 11 )"));
        assertEquals("4 1 2 * 3 - * 11 cos +", translate("4 * ( 1 * 2 - 3 ) + cos ( 11 )"));
        assertEquals("5 sin 4 1 2 * 3 - * 11 cos + +", translate("sin ( 5 ) + ( 4 * ( 1 * 2 - 3 ) + cos ( 11 ) )"));
        assertEquals("1 2 sqr +", translate("1 + sqr ( 2 )"));
        assertEquals("1 2 * 5 7 pwr +", translate("1 * 2 + pwr ( 5 , 7 )"));
        assertEquals("4 1 2 * 3 - 10 7 6 * + sqr pwr + 10 3 * + 5 cos +",
                translate("4 + pwr ( 1 * 2 - 3 , sqr ( 10 + 7 * 6 ) ) + 10 * 3 + cos ( 5 )"));


        assertEquals(1.1, 1.1000001, 0.0001);
    }

}