package test2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class test2Test {
    @ParameterizedTest
    @CsvSource({
            "1,2,3,非三角形",
            "-1,2,3,输入错误"
    })
    void testWithCsvSource(int a,int b,int c,String expected) {
        assertEquals(expected, test2.classify(a,b,c));
    }
    @DisplayName("一般边界值测试")
    @ParameterizedTest
    @CsvFileSource(resources = "/ordinary.csv")
    void test_ordinary(int a, int b, int c, String expected) {
        assertEquals(expected, test2.classify(a,b,c));
    }

    @DisplayName("健壮性边界值测试")
    @ParameterizedTest
    @CsvFileSource(resources = "/triangle.csv")
    void test_triangle(int a, int b, int c, String expected) {
        assertEquals(expected, test2.classify(a,b,c));
    }

    @DisplayName("最坏情况边界值测试")
    @ParameterizedTest
    @CsvFileSource(resources = "/worst_ordinary.csv")
    void test_worst_ordinary(int a, int b, int c, String expected) {
        assertEquals(expected, test2.classify(a,b,c));
    }

    @DisplayName("最坏情况健壮性边界值测试")
    @ParameterizedTest
    @CsvFileSource(resources = "/worst_triangle.csv")
    void test_worst_triangle(int a, int b, int c, String expected) {
        assertEquals(expected, test2.classify(a,b,c));
    }
}