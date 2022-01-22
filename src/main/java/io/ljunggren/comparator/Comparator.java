package io.ljunggren.comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.ljunggren.comparator.annotation.Comparable;
import io.ljunggren.comparator.chain.ArrayDiff;
import io.ljunggren.comparator.chain.CatchAllDiff;
import io.ljunggren.comparator.chain.CollectionDiff;
import io.ljunggren.comparator.chain.DiffChain;
import io.ljunggren.comparator.chain.MapDiff;
import io.ljunggren.comparator.chain.PrimitiveDiff;
import io.ljunggren.comparator.utils.ComparatorUtils;

public class Comparator<T> {

    private T object1;
    private T object2;
    private ComparatorUtils<T> comparatorUtils = new ComparatorUtils<>();
    
    public Comparator(T object1, T object2) {
        this.object1 = object1;
        this.object2 = object2;
    }
    
    public boolean isEqual() {
        return compare().isEmpty();
    }
    
    public List<Diff> compare() {
        if (object1 == null && object2 == null) {
            return new ArrayList<Diff>();
        }
        if (object1 == null || object2 == null) {
            return findNullDiffs(object1, object2);
        }
        return findDiffs(object1, object2);
    }
    
    private List<Diff> findNullDiffs(T object1, T object2) {
        if (object1 == null) {
            List<Item> items2 = comparatorUtils.findItems(object2);
            List<Item> items1 = buildEmptyItems(items2);
            return findDiffs(items1, items2);
        }
        List<Item> items1 = comparatorUtils.findItems(object1);
        List<Item> items2 = buildEmptyItems(items1);
        return findDiffs(items1, items2);
    }
    
    private List<Item> buildEmptyItems(List<Item> items) {
        return items.stream().map(item -> 
                new Item(item.getObject(), item.getField(), null)
        ).collect(Collectors.toList());
    }
    
    private List<Diff> findDiffs(T object1, T object2) {
        List<Item> items1 = comparatorUtils.findItems(object1);
        List<Item> items2 = comparatorUtils.findItems(object2);
        return findDiffs(items1, items2);
    }
    
    private List<Diff> findDiffs(List<Item> items1, List<Item> items2) {
        List<Diff> diffs = new ArrayList<>();
        for (int i = 0; i < items1.size(); i++) {
            Item item1 = items1.get(i);
            Item item2 = items2.get(i);
            if (isComparable(item1)) {
                diffs.addAll(getChain().findDiffs(item1, item2));
            }
        }
        return diffs;
    }
    
    private boolean isComparable(Item item) {
        return item.getField().getAnnotation(Comparable.class) != null;
    }
    
    private DiffChain getChain() {
        return new PrimitiveDiff().nextChain(
                new CollectionDiff().nextChain(
                new ArrayDiff().nextChain(
                new MapDiff().nextChain(
                new CatchAllDiff()
        ))));
    }
    
}
