package com.ljunggren.comparator.chain;

import java.util.ArrayList;
import java.util.List;

import com.ljunggren.comparator.Comparator;
import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.Item;

public class CatchAllDiff extends DiffChain {

    @Override
    public List<Diff> findDiffs(Item item1, Item item2) {
        if (item1 == null && item2 == null) {
            return new ArrayList<>();
        }
        return new Comparator<Object>(item1.getValue(), item2.getValue()).compare();
    }

}
