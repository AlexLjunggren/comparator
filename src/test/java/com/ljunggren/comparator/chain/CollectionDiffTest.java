package com.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.Item;
import com.ljunggren.comparator.utils.ComparatorUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CollectionDiffTest {
    
    @Data
    @AllArgsConstructor
    private class User {
        Collection<String> aliases;
    }
    
    private ComparatorUtils<User> comparatorUtils = new ComparatorUtils<>();
    
    private DiffChain getChain() {
        return new CollectionDiff().nextChain(new CatchAllDiff());
    }
    
    private List<Diff> findDiffs(List<String> aliases1, List<String> aliases2) {
        User user1 = new User(aliases1);
        User user2 = new User(aliases2);
        List<Item> items1 = comparatorUtils.findItems(user1);
        List<Item> items2 = comparatorUtils.findItems(user2);
        return getChain().findDiffs(items1.get(0), items2.get(0));
    }

    @Test
    public void findDiffTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {
                "Chris", "Alex"
        }));
        List<Diff> diffs = findDiffs(names1, names2);
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
        List<Diff> diffs = findDiffs(names1, names2);
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
        List<Diff> diffs = findDiffs(names1, names2);
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffEmptyTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {}));
        List<Diff> diffs = findDiffs(names1, names2);
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNullTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<Diff> diffs = findDiffs(names1, null);
        assertEquals(2, diffs.size());
    }

    @Test
    public void findDiffNullFromOtherObjectTest() {
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<Diff> diffs = findDiffs(null, names2);
        assertEquals(2, diffs.size());
    }
    
    @Test
    public void findDiffBothNullTest() {
        List<Diff> diffs = findDiffs(null, null);
        assertEquals(0, diffs.size());
    }

    @Test
    public void findDiffNoDiffTest() {
        List<String> names1 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<String> names2 = new ArrayList<>(Arrays.asList(new String[] {
                "James", "Alex"
        }));
        List<Diff> diffs = findDiffs(names1, names2);
        assertEquals(0, diffs.size());
    }

}
