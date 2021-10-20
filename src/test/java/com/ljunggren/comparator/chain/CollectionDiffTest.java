package com.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.Comparator;
import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.annotation.Comparable;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CollectionDiffTest {
    
    @Data
    @AllArgsConstructor
    private class User {
        @Comparable
        Collection<String> aliases;
    }
    

    @Test
    public void findDiffTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {
                "Chris", "Alex"
        }));
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(1, diffs.size());
        assertEquals(names1.get(0), diffs.get(0).getValue1());
        assertEquals(names2.get(0), diffs.get(0).getValue2());
    }
    
    @Test
    public void findDiffMoreTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "Chris", "Alex", "James"
        }));
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {
                "Chris", "Alex"
        }));
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffMoreFromOtherObjectTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "Chris", "Alex"
        }));
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {
                "Chris", "Alex", "James"
        }));
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffEmptyCollectionTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {}));
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNullCollectionTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        User user1 = new User(names1);
        User user2 = new User(null);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNullCollectionFromOtherObjectTest() {
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        User user1 = new User(null);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(2, diffs.size());
    }
    
    @Test
    public void findDiffNoDiffTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        User user1 = new User(names1);
        User user2 = new User(names2);
        List<Diff> diffs = new Comparator<>(user1, user2).compare();
        assertEquals(0, diffs.size());
    }

}
