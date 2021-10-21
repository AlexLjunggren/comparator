package com.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.Item;
import com.ljunggren.comparator.utils.ComparatorUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MapDiffTest {
    
    @Data
    @AllArgsConstructor
    private class Book {
        Map<Integer, String> chapters;
    }
    
    private ComparatorUtils<Book> comparatorUtils = new ComparatorUtils<>();
    
    private DiffChain getChain() {
        return new MapDiff().nextChain(new CatchAllDiff());
    }
    
    private List<Diff> findDiffs(Map<Integer, String> chapters1, Map<Integer, String> chapters2) {
        Book mobyDick1 = new Book(chapters1);
        Book mobyDick2 = new Book(chapters2);
        List<Item> items1 = comparatorUtils.findItems(mobyDick1);
        List<Item> items2 = comparatorUtils.findItems(mobyDick2);
        return getChain().findDiffs(items1.get(0), items2.get(0));
    }
    
    private Map<Integer, String> getChapters() {
        Map<Integer, String> chapters = new HashMap<>();
        chapters.put(1, "Loomings");
        chapters.put(2, "The Carpet-Bag");
        chapters.put(3, "The Spouter-Inn");
        chapters.put(4, "The Counterpane");
        chapters.put(5, "Breakfast");
        chapters.put(6, "The Street");
        return chapters;
    }
    
    @Test
    public void findDiffTest() {
        Map<Integer, String> chapters1 = getChapters();
        Map<Integer, String> chapters2 = getChapters();
        chapters2.remove(6);
        List<Diff> diffs = findDiffs(chapters1, chapters2);
        assertEquals(1, diffs.size());
    }
    
    @Test
    public void findDiffEmptyMapTest() {
        Map<Integer, String> chapters1 = getChapters();
        Map<Integer, String> chapters2 = new HashMap<>();
        List<Diff> diffs = findDiffs(chapters1, chapters2);
        assertEquals(6, diffs.size());
    }
    
    @Test
    public void findDiffEmptyMapFromTheOtherObjectTest() {
        Map<Integer, String> chapters1 = new HashMap<>();
        Map<Integer, String> chapters2 = getChapters();
        List<Diff> diffs = findDiffs(chapters1, chapters2);
        assertEquals(6, diffs.size());
    }
    
    @Test
    public void findDiffNullMapTest() {
        Map<Integer, String> chapters1 = getChapters();
        List<Diff> diffs = findDiffs(chapters1, null);
        assertEquals(6, diffs.size());
    }

    @Test
    public void findDiffNullMapFromOtherObjectTest() {
        Map<Integer, String> chapters2 = getChapters();
        List<Diff> diffs = findDiffs(null, chapters2);
        assertEquals(6, diffs.size());
    }

    @Test
    public void findDiffBothNullTest() {
        List<Diff> diffs = findDiffs(null, null);
        assertEquals(0, diffs.size());
    }

    @Test
    public void findDiffNoDiffTest() {
        Map<Integer, String> chapters1 = getChapters();
        Map<Integer, String> chapters2 = getChapters();
        List<Diff> diffs = findDiffs(chapters1, chapters2);
        assertEquals(0, diffs.size());
    }

}
