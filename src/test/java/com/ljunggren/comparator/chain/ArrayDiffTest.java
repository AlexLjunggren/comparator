package com.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.Comparator;
import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.annotation.Comparable;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ArrayDiffTest {
    
    @Data
    @AllArgsConstructor
    private class User {
        @Comparable
        String[] aliases;
    }
    
    @Test
    public void findDiffTest() {
        String[] names1 = new String[] {
                "James", "Alex"
        };
        String[] names2 = new String[] {
                "Chris", "Alex"
        };
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(1, diffs.size());
        assertEquals(names1[0], diffs.get(0).getValue1());
        assertEquals(names2[0], diffs.get(0).getValue2());
    }

    @Test
    public void findDiffMoreTest() {
        String[] names1 = new String[] {
                "Chris", "Alex", "James"
        };
        String[] names2 = new String[] {
                "Chris", "Alex"
        };
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffMoreFromOtherObjectTest() {
        String[] names1 = new String[] {
                "Chris", "Alex"
        };
        String[] names2 = new String[] {
                "Chris", "Alex", "James"
        };
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffEmptyArrayTest() {
        String[] names1 = new String[] {
                "James", "Alex"
        };
        String[] names2 = new String[] {};
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNullArrayTest() {
        String[] names1 = new String[] {
                "James", "Alex"
        };
        User user1 = new User(names1);
        User user2 = new User(null);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNullArrayFromOtherObjecyTest() {
        String[] names2 = new String[] {
                "James", "Alex"
        };
        User user1 = new User(null);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNoDiffTest() {
        String[] names1 = new String[] {
                "James", "Alex"
        };
        String[] names2 = new String[] {
                "James", "Alex"
        };
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(0, diffs.size());
    }


}
