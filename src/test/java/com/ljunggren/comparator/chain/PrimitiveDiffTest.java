package com.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.Adaptor;
import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.Item;
import com.ljunggren.comparator.utils.ComparatorUtils;

public class PrimitiveDiffTest {
    
    private DiffChain getChain() {
        return new PrimitiveDiff().nextChain(new CatchAllDiff());
    }
    
    @Test
    public void findDiffIsPrimitiveTest() {
        ComparatorUtils<Adaptor<Boolean>> comparatorUtils = new ComparatorUtils<>();
        List<Item> items1 = comparatorUtils.findItems(new Adaptor<>(true));
        List<Item> items2 = comparatorUtils.findItems(new Adaptor<>(false));
        List<Diff> diffs = getChain().findDiffs(items1.get(0), items2.get(0));
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffIsStringTest() {
        ComparatorUtils<Adaptor<String>> comparatorUtils = new ComparatorUtils<>();
        List<Item> items1 = comparatorUtils.findItems(new Adaptor<>("Alex"));
        List<Item> items2 = comparatorUtils.findItems(new Adaptor<>("James"));
        List<Diff> diffs = getChain().findDiffs(items1.get(0), items2.get(0));
        assertEquals(1, diffs.size());
    }

}
