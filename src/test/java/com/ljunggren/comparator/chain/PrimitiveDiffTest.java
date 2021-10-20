package com.ljunggren.comparator.chain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.ljunggren.comparator.Adaptor;
import com.ljunggren.comparator.Comparator;
import com.ljunggren.comparator.Diff;

public class PrimitiveDiffTest {

    @Test
    public void findDiffIsPrimitiveTest() {
        List<Diff> diffs = new Comparator<>(new Adaptor<>(true), new Adaptor<>(false)).compare();
        assertEquals(1, diffs.size());
    }

    @Test
    public void findDiffIsStringTest() {
        List<Diff> diffs = new Comparator<>(new Adaptor<>("Alex"), new Adaptor<>("James")).compare();
        assertEquals(1, diffs.size());
    }

}
