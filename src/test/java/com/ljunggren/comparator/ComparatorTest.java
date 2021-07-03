package com.ljunggren.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    public void isEqualFalseTest() {
        User alex = new User("Alex", true);
        User james = new User("James", false);
        assertFalse(new Comparator(alex, james).isEqual());
    }
    
    @Test
    public void isEqualBothNullTest() {
        assertTrue(new Comparator(null, null).isEqual());
    }

    @Test
    public void isEqualTrueTest() {
        User alex = new User("Alex", true);
        User alexander = new User("Alex", false);
        assertTrue(new Comparator(alex, alexander).isEqual());
    }

    @Test
    public void compareTest() {
        User alex = new User("Alex", true);
        User james = new User("James", false);
        List<Diff> diffs = new Comparator(alex, james).compare();
        assertEquals(1, diffs.size());
        assertEquals("name", diffs.get(0).getName());
    }
    
    @Test
    public void compareFirstIsNullTest() {
        User alex = null;
        User james = new User("James", false);
        List<Diff> diffs = new Comparator(alex, james).compare();
        Diff diff = diffs.get(0);
        assertEquals(1, diffs.size());
        assertEquals("name", diff.getName());
        assertNull(diff.getValue1());
        assertEquals("James", diff.getValue2());
    }
    
    @Test
    public void comareSecondIsNullTest() {
        User alex = new User("Alex", true);
        User james = null;
        List<Diff> diffs = new Comparator(alex, james).compare();
        Diff diff = diffs.get(0);
        assertEquals(1, diffs.size());
        assertEquals("name", diff.getName());
        assertEquals("Alex", diff.getValue1());
        assertNull(diff.getValue2());
    }
    
    @Test(expected = ComparatorException.class)
    public void compareUnlikeObjectTest() throws ComparatorException {
        new Comparator(new String(), new Double(0)).compare();
    }

}
