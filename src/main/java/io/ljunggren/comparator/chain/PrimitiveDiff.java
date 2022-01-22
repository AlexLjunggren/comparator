package io.ljunggren.comparator.chain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.ljunggren.comparator.Diff;
import io.ljunggren.comparator.Item;
import io.ljunggren.reflectionUtils.ReflectionUtils;

public class PrimitiveDiff extends DiffChain {

    @Override
    public List<Diff> findDiffs(Item item1, Item item2) {
        if (isPrimitiveObject(item1.getValue()) || isPrimitiveObject(item2.getValue())) {
            Diff diff = findDiff(item1, item2);
            return diff == null ? new ArrayList<>() : 
                new ArrayList<>(Arrays.asList(new Diff[] { diff }));
        }
        return nextChain.findDiffs(item1, item2);
    }

    private boolean isPrimitiveObject(Object object) {
        if (object == null) {
            return false;
        }
        Class<?> clazz = object.getClass();
        return ReflectionUtils.isPrimitive(clazz) || 
                ReflectionUtils.isString(clazz) ||
                ReflectionUtils.isBoolean(clazz);
    }
    
}
