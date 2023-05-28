package test5;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    @Test
    void should_remove_first_null_element_when_remove_null(){
        List<String> list = new LinkedList<>();
        list.add("yes");
        list.add("no");
        list.remove(null);
        assertEquals(2,list.size());
    }
    @Test
    void nlshould_remove_first_null_element_when_remove_null(){
        List<String> list = new LinkedList<>();
        list.add(null);
        list.add(null);
        list.remove(null);
        assertEquals(1,list.size());
    }
    @Test
    void noshould_remove_first_null_element_when_remove_null(){
        List<String> list = new LinkedList<>();
        list.add("yes");
        list.add("no");
        list.remove("noyes");
        assertEquals(2,list.size());
    }
    @Test
    void nullshould_remove_first_null_element_when_remove_null(){
        List<String> list = new LinkedList<>();
        list.add("yes");
        list.add("no");
        list.remove("yes");
        assertEquals(1,list.size());
    }
}