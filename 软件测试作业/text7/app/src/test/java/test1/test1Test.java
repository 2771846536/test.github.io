package test1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class test1Test {
    @Test
    @DisplayName("输入错误")
    void parameters_error_test() {
        test1 triangle = new test1();
        String type = triangle.getGreeting(0, 4, 5);
        assertEquals("输入错误", type);
    }

    @Test
    @DisplayName(value = "不等边三角形")
    void testTriangle() {
        test1 triangle = new test1();

        String type = triangle.getGreeting(3, 4, 6);
        assertEquals("不等边三角形", type);
    }

    @Test
    @DisplayName("等边三角形")
    void equallaterialTriangle() {
        test1 triangle = new test1();

        String type = triangle.getGreeting(3, 3, 3);
        assertEquals("等边三角形", type);
    }

    @Test
    @DisplayName("非三角形")

    void notTriangle() {
        test1 triangle = new test1();

        String type = triangle.getGreeting(3, 3, 6);
        assertEquals("非三角形", type);
    }
    @Test
    @DisplayName("等腰三角形")

    void isosceles() {
        test1 triangle = new test1();

        String type = triangle.getGreeting(3, 3, 4);
        assertEquals("等腰三角形", type);
    }
    @ParameterizedTest
    @CsvSource({
            "3,4,6,不等边三角形",
            "3,3,3,等边三角形",
            "3,3,6,非三角形"
    })
    @DisplayName("测试用例")
    void paramTriangle(int a, int b, int c, String expected) {
        test1 triangle = new test1();

        String type = triangle.getGreeting(a, b, c);

        assertEquals(expected, type);
    }
}