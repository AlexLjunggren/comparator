package com.ljunggren.comparator.utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.Item;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ComparatorUtilsTest {
    
    @Data
    @AllArgsConstructor
    private class User {
        private String name;
        private int year;
    }
    
    private ComparatorUtils<User> comparatorUtils = new ComparatorUtils<>();

    @Test
    public void test() {
        List<Item> items = comparatorUtils.findItems(new User("Alex", 2021));
        assertEquals(3, items.size());
    }

}
