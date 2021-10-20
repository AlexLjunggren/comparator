package com.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ljunggren.comparator.Comparator;
import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.annotation.Comparable;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MapDiffTest {
    
    @Data
    @AllArgsConstructor
    private class Book {
        @Comparable
        Map<Integer, String> chapters;
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
        Book mobyDick = new Book(getChapters());
        Book abridgedMobyDick = new Book(getChapters());
        abridgedMobyDick.getChapters().remove(6);
        List<Diff> diffs = new Comparator<>(mobyDick, abridgedMobyDick).compare();
        assertEquals(1, diffs.size());
    }
    
    @Test
    public void findDiffEmptyMapTest() {
        Book mobyDick = new Book(getChapters());
        Book abridgedMobyDick = new Book(new HashMap<>());
        List<Diff> diffs = new Comparator<>(mobyDick, abridgedMobyDick).compare();
        assertEquals(6, diffs.size());
    }
    
    @Test
    public void findDiffEmptyMapFromTheOtherObjectTest() {
        Book mobyDick = new Book(new HashMap<>());
        Book abridgedMobyDick = new Book(getChapters());
        List<Diff> diffs = new Comparator<>(mobyDick, abridgedMobyDick).compare();
        assertEquals(6, diffs.size());
    }
    
    @Test
    public void findDiffNullMapTest() {
        Book mobyDick = new Book(getChapters());
        Book abridgedMobyDick = new Book(null);
        List<Diff> diffs = new Comparator<>(mobyDick, abridgedMobyDick).compare();
        assertEquals(6, diffs.size());
    }

    @Test
    public void findDiffNullMapFromOtherObjectTest() {
        Book mobyDick = new Book(null);
        Book abridgedMobyDick = new Book(getChapters());
        List<Diff> diffs = new Comparator<>(mobyDick, abridgedMobyDick).compare();
        assertEquals(6, diffs.size());
    }

    @Test
    public void findDiffBothNullTest() {
        Book mobyDick = new Book(null);
        Book abridgedMobyDick = new Book(null);
        List<Diff> diffs = new Comparator<>(mobyDick, abridgedMobyDick).compare();
        assertEquals(0, diffs.size());
    }

    @Test
    public void findDiffNoDiffTest() {
        Book mobyDick = new Book(getChapters());
        Book abridgedMobyDick = new Book(getChapters());
        List<Diff> diffs = new Comparator<>(mobyDick, abridgedMobyDick).compare();
        assertEquals(0, diffs.size());
    }

}
