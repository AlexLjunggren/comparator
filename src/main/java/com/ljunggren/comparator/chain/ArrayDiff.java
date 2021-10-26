package com.ljunggren.comparator.chain;

import java.util.ArrayList;
import java.util.List;

import com.ljunggren.comparator.Adaptor;
import com.ljunggren.comparator.Comparator;
import com.ljunggren.comparator.Diff;
import com.ljunggren.comparator.Item;
import com.ljunggren.reflectionUtils.ReflectionUtils;

public class ArrayDiff extends DiffChain {

    @Override
    public List<Diff> findDiffs(Item item1, Item item2) {
        if (isArray(item1.getValue()) || isArray(item2.getValue())) {
            return findArrayDiffs(item1.getValue(), item2.getValue());
        }
        return nextChain.findDiffs(item1, item2);
    }
    
    private boolean isArray(Object object) {
        if (object == null) {
            return false;
        }
        return ReflectionUtils.isArray(object.getClass());
    }
    
    private List<Diff> findArrayDiffs(Object object1, Object object2) {
        Object[] array1 = (Object[]) object1;
        Object[] array2 = (Object[]) object2;
        return findArrayDiffs(array1, array2);
    }
    
    private List<Diff> findArrayDiffs(Object[] array1, Object[] array2) {
        List<Diff> diffs = new ArrayList<>();
        long array1Size = array1 == null ? 0 : array1.length;
        long array2Size = array2 == null ? 0 : array2.length;
        long size = array1Size > array2Size ? array1Size : array2Size;
        for (int i = 0; i < size; i++) {
            if (i >= array1Size) {
                diffs.addAll(new Comparator<>(null, new Adaptor<>(array2[i])).compare());
                continue;
            }
            if (i >= array2Size) {
                diffs.addAll(new Comparator<>(new Adaptor<>(array1[i]), null).compare());
                continue;
            }
            diffs.addAll(new Comparator<>(new Adaptor<>(array1[i]), new Adaptor<>(array2[i])).compare());
        }
        return diffs;
    }

}
