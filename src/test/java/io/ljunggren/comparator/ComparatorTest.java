package io.ljunggren.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import io.ljunggren.comparator.annotation.Comparable;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ComparatorTest {
    
    @Data
    @AllArgsConstructor
    private class User {
        @Comparable
        private String name;
        @Comparable
        private Address address;
        private boolean active;
    }
    
    @Data
    @AllArgsConstructor
    private class Address {
        @Comparable
        private String city;
        private String state;
    }
    
    private Address getAddress() {
        return new Address("Indianapolis", "IN");
    }
    
    @Test
    public void isEqualFalseTest() {
        User alex = new User("Alex", getAddress(), true);
        User james = new User("James", getAddress(), false);
        assertFalse(new Comparator<User>(alex, james).isEqual());
    }
    
    @Test
    public void isEqualBothNullTest() {
        assertTrue(new Comparator<User>(null, null).isEqual());
    }

    @Test
    public void isEqualTrueTest() {
        User alex = new User("Alex", getAddress(), true);
        User alexander = new User("Alex", getAddress(), false);
        assertTrue(new Comparator<User>(alex, alexander).isEqual());
    }

    @Test
    public void compareTest() {
        User alex = new User("Alex", getAddress(), true);
        User james = new User("James", getAddress(), false);
        List<Diff> diffs = new Comparator<User>(alex, james).compare();
        assertEquals(1, diffs.size());
        assertEquals("name", diffs.get(0).getName());
    }
    
    @Test
    public void compareFirstIsNullTest() {
        User alex = null;
        User james = new User("James", getAddress(), false);
        List<Diff> diffs = new Comparator<User>(alex, james).compare();
        Diff diff1 = diffs.get(0);
        Diff diff2 = diffs.get(1);
        assertEquals(2, diffs.size());
        assertEquals("name", diff1.getName());
        assertNull(diff1.getValue1());
        assertEquals("James", diff1.getValue2());
        assertEquals("city", diff2.getName());
        assertNull(diff2.getValue1());
        assertEquals("Indianapolis", diff2.getValue2());
    }
    
    @Test
    public void compareSecondIsNullTest() {
        User alex = new User("Alex", getAddress(), true);
        User james = null;
        List<Diff> diffs = new Comparator<User>(alex, james).compare();
        Diff diff1 = diffs.get(0);
        Diff diff2 = diffs.get(1);
        assertEquals(2, diffs.size());
        assertEquals("name", diff1.getName());
        assertEquals("Alex", diff1.getValue1());
        assertNull(diff1.getValue2());
        assertEquals("city", diff2.getName());
        assertEquals("Indianapolis", diff2.getValue1());
        assertNull(diff2.getValue2());
    }
    
    @Test
    public void compareEmbeddedObjectTest() {
        User alex = new User("Alex", getAddress(), true);
        User alexander = new User("Alex", new Address("New York City", "NY"), true);
        List<Diff> diffs = new Comparator<User>(alex, alexander).compare();
        assertEquals(1, diffs.size());
    }
    
    @Test
    public void compareClassTest() {
        User alex = new User("Alex", getAddress(), true);
        User james = new User("James", new Address("New York City", "NY"), true);
        List<Diff> diffs = new Comparator<User>(alex, james).compare();
        assertEquals(User.class, diffs.get(0).getDeclaringClass());
        assertEquals(Address.class, diffs.get(1).getDeclaringClass());
    }
    
}
