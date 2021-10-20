package com.ljunggren.comparator.chain;

import java.util.List;

import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.Item;

public abstract class DiffChain {
    
    protected DiffChain nextChain;
    
    public DiffChain nextChain(DiffChain nextChain) {
        this.nextChain = nextChain;
        return this;
    }

    protected Diff buildDiff(Item item1, Item item2) {
        return Diff.builder()
                .name(item1.getField().getName())
                .value1(item1.getValue())
                .value2(item2.getValue())
                .declaringClass(item1.getField().getDeclaringClass())
                .build();
    }
    
    protected Diff findDiff(Item item1, Item item2) {
        Object value1 = item1.getValue();
        Object value2 = item2.getValue();
        if (value1 == null || value2 == null) {
            return buildDiff(item1, item2);
        }
        if (!value1.equals(value2)) {
            return buildDiff(item1, item2);
        }
        return null;
    }
    
    public abstract List<Diff> findDiffs(Item item1, Item item2);
    
}
