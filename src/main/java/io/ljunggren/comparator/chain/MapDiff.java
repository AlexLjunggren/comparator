package io.ljunggren.comparator.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.ljunggren.comparator.Adaptor;
import io.ljunggren.comparator.Comparator;
import io.ljunggren.comparator.Diff;
import io.ljunggren.comparator.Item;
import io.ljunggren.reflectionUtils.ReflectionUtils;

public class MapDiff extends DiffChain {

    @Override
    public List<Diff> findDiffs(Item item1, Item item2) {
        if (isMap(item1.getValue()) || isMap(item2.getValue())) {
            return findMapDiffs(item1, item2);
        }
        return nextChain.findDiffs(item1, item2);
    }
    
    private boolean isMap(Object object) {
        if (object == null) {
            return false;
        }
        return ReflectionUtils.isMap(object.getClass());
    }
    
    private List<Diff> findMapDiffs(Item item1, Item item2) {
        List<Diff> diffs = new ArrayList<>();
        Map<?, ?> map1 = (Map<?, ?>) item1.getValue();
        Map<?, ?> map2 = (Map<?, ?>) item2.getValue();
        if (map1 == null) {
            return buildDiffsForNullMap1(map2, item2);
        }
        if (map2 == null) {
            return buildDiffsForNullMap2(map1, item1);
        }
        if (map1.size() > map2.size()) {
            for (Map.Entry<?, ?> entry: map1.entrySet()) {
                Object value2 = map2.get(entry.getKey());
                diffs.addAll(new Comparator<>(new Adaptor<>(entry.getValue()), new Adaptor<>(value2)).compare());
            }
            return diffs;
        }
        for (Map.Entry<?, ?> entry: map2.entrySet()) {
            Object value1 = map1.get(entry.getKey());
            diffs.addAll(new Comparator<>(new Adaptor<>(value1), new Adaptor<>(entry.getValue())).compare());
        }
        return diffs;
    }
    
    private List<Diff> buildDiffsForNullMap1(Map<?, ?> map2, Item item2) {
        List<Diff> diffs = new ArrayList<>();
        for (Map.Entry<?, ?> entry: map2.entrySet()) {
            diffs.add(Diff.builder()
                    .name(item2.getField().getName())
                    .value1(null)
                    .value2(entry)
                    .declaringClass(item2.getField().getDeclaringClass())
                    .build());
        }
        return diffs;
    }
    
    private List<Diff> buildDiffsForNullMap2(Map<?, ?> map1, Item item1) {
        List<Diff> diffs = new ArrayList<>();
        for (Map.Entry<?, ?> entry: map1.entrySet()) {
            diffs.add(Diff.builder()
                    .name(item1.getField().getName())
                    .value1(entry)
                    .value2(null)
                    .declaringClass(item1.getField().getDeclaringClass())
                    .build());
        }
        return diffs;
    }

}
