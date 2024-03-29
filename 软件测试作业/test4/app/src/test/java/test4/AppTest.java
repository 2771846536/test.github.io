/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package test4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
class AppTest {
    @ParameterizedTest
    @DisplayName("决策表测试")
    @CsvFileSource(resources = "/test4.csv")
    void fileTest1(String fromtime, String totime, String expected) {
        App phoneFee = new App();
        assertEquals(expected, phoneFee.cost(fromtime, totime));
    }
}