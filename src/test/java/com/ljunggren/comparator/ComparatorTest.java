package com.ljunggren.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.annotation.Comparable;
import com.ljunggren.comparator.exception.ComparatorException;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ComparatorTest {
    
    @Data
    @AllArgsConstructor
    private class User {
        @Comparable
        private String name;
        private boolean active;
    }
    
    @Test
    public void isEqualTest() throws ComparatorException {
        User alex = new User("Alex", true);
        User james = new User("James", false);
        assertFalse(new Comparator(alex, james).isEqual());
    }

    @Test
    public void compareTest() throws ComparatorException {
        User alex = new User("Alex", true);
        User james = new User("James", false);
        List<Diff> diffs = new Comparator(alex, james).compare();
        assertEquals("name", diffs.get(0).getName());
    }
    
    @Test(expected = ComparatorException.class)
    public void compareUnlikeObjectTest() throws ComparatorException {
        new Comparator(new String(), new Double(0)).compare();
    }

}
