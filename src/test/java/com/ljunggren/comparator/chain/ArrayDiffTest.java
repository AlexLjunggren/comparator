package com.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.Item;
import com.ljunggren.comparator.utils.ComparatorUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ArrayDiffTest {
    
    @Data
    @AllArgsConstructor
    private class User {
         String[] aliases;
    }
    
    private ComparatorUtils<User> comparatorUtils = new ComparatorUtils<>();
    
    private DiffChain getChain() {
        return new ArrayDiff().nextChain(new CatchAllDiff());
    }
    
    private List<Diff> findDiffs(String[] aliases1, String[] aliases2) {
        User user1 = new User(aliases1);
        User user2 = new User(aliases2);
        List<Item> items1 = comparatorUtils.findItems(user1);
        List<Item> items2 = comparatorUtils.findItems(user2);
        return getChain().findDiffs(items1.get(0), items2.get(0));
    }

    @Test
    public void findDiffTest() {
        String[] aliases1 = new String[] {
                "James", "Alex"
        };
        String[] aliases2 = new String[] {
                "Chris", "Alex"
        };
        List<Diff> diffs = findDiffs(aliases1, aliases2);
        assertEquals(1, diffs.size());
        assertEquals(aliases1[0], diffs.get(0).getValue1());
        assertEquals(aliases2[0], diffs.get(0).getValue2());
    }
    
    @Test
    public void findDiffMoreTest() {
        String[] aliases1 = new String[] {
                "Chris", "Alex", "James"
        };
        String[] aliases2 = new String[] {
                "Chris", "Alex"
        };
        List<Diff> diffs = findDiffs(aliases1, aliases2);
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffMoreFromOtherObjectTest() {
        String[] aliases1 = new String[] {
                "Chris", "Alex"
        };
        String[] aliases2 = new String[] {
                "Chris", "Alex", "James"
        };
        List<Diff> diffs = findDiffs(aliases1, aliases2);
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffEmptyTest() {
        String[] aliases1 = new String[] {
                "James", "Alex"
        };
        String[] aliases2 = new String[] {};
        List<Diff> diffs = findDiffs(aliases1, aliases2);
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNullTest() {
        String[] aliases1 = new String[] {
                "James", "Alex"
        };
        List<Diff> diffs = findDiffs(aliases1, null);
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNullFromOtherObjectTest() {
        String[] aliases2 = new String[] {
                "James", "Alex"
        };
        List<Diff> diffs = findDiffs(null, aliases2);
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffBothNullTest() {
        List<Diff> diffs = findDiffs(null, null);
        assertEquals(0, diffs.size());
    }

    @Test
    public void findDiffNoDiffTest() {
        String[] aliases1 = new String[] {
                "James", "Alex"
        };
        String[] aliases2 = new String[] {
                "James", "Alex"
        };
        List<Diff> diffs = findDiffs(aliases1, aliases2);
        assertEquals(0, diffs.size());
    }

}
