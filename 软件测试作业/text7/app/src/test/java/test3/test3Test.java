package test3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class test3Test {
    @ParameterizedTest
    @DisplayName("等价类测试")
    @CsvFileSource(resources= "/test3.csv")
    void fileTest1(String fromtime,String totime,String expected){
        test3 phoneFee=new test3();
        assertEquals(expected,phoneFee.GetMoney(fromtime,totime));
    }
}