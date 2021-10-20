package com.ljunggren.comparator.chain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.Diff;

public class CatchAllDiffTest {
    
    private DiffChain getChain() {
        return new CatchAllDiff();
    }

    @Test
    public void findDiffBothNullTest() {
        List<Diff> diffs = getChain().findDiffs(null, null);
        assertEquals(0, diffs.size());
    }

}
