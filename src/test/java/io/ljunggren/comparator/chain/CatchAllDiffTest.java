package io.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import io.ljunggren.comparator.Diff;

public class CatchAllDiffTest {
    
    @Test
    public void findDiffBothNullTest() {
        List<Diff> diffs = new CatchAllDiff().findDiffs(null, null);
        assertEquals(0, diffs.size());
    }

}
