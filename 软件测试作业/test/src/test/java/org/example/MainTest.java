package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    @DisplayName("输入错误")
    void parameters_error_test() {
        Main triangle = new Main();
        String type = triangle.getGreeting(0, 4, 5);
        assertEquals("输入错误", type);
    }

    @Test
    @DisplayName(value = "不等边三角形")
    void testTriangle() {
        Main triangle = new Main();

        String type = triangle.getGreeting(3, 4, 6);
        assertEquals("不等边三角形", type);
    }

    @Test
    @DisplayName("等边三角形")
    void equallaterialTriangle() {
        Main triangle = new Main();

        String type = triangle.getGreeting(3, 3, 3);
        assertEquals("等边三角形", type);
    }

    @Test
    @DisplayName("非三角形")

    void notTriangle() {
        Main triangle = new Main();

        String type = triangle.getGreeting(3, 3, 6);
        assertEquals("非三角形", type);
    }
    @Test
    @DisplayName("等腰三角形")

    void isosceles() {
        Main triangle = new Main();

        String type = triangle.getGreeting(3, 3, 4);
        assertEquals("等腰三角形", type);
    }
    @ParameterizedTest
    @CsvSource({
            "3,4,6,不等边三角形",
            "5,5,5,等边三角形",
            "3,3,10,非三角形"
    })
    @DisplayName("测试用例")
    void paramTriangle(int a, int b, int c, String expected) {
        Main triangle = new Main();

        String type = triangle.getGreeting(a, b, c);

        assertEquals(expected, type);
    }

}