package io.ljunggren.comparator.chain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.ljunggren.comparator.Adaptor;
import io.ljunggren.comparator.Comparator;
import io.ljunggren.comparator.Diff;
import io.ljunggren.comparator.Item;
import io.ljunggren.reflectionUtils.ReflectionUtils;

public class CollectionDiff extends DiffChain {

    @Override
    public List<Diff> findDiffs(Item item1, Item item2) {
        if (isCollection(item1.getValue()) || isCollection(item2.getValue())) {
            return findCollectionDiffs(item1, item2);
        }
        return nextChain.findDiffs(item1, item2);
    }
    
    private boolean isCollection(Object object) {
        if (object == null) {
            return false;
        }
        return ReflectionUtils.isCollection(object.getClass());
    }
    
    private List<Diff> findCollectionDiffs(Item item1, Item item2) {
        Collection<?> collection1 = (Collection<?>) item1.getValue();
        Collection<?> collection2 = (Collection<?>) item2.getValue();
        return findCollectionDiffs(collection1, collection2);
    }
    
    private List<Diff> findCollectionDiffs(Collection<?> collection1, Collection<?> collection2) {
        List<Diff> diffs = new ArrayList<>();
        long collection1Size = collection1 == null ? 0 : collection1.size();
        long collection2Size = collection2 == null ? 0 : collection2.size();
        long size = collection1Size > collection2Size ? collection1Size : collection2Size;
        for (int i = 0; i < size; i++) {
            if (i >= collection1Size) {
                diffs.addAll(new Comparator<>(null, new Adaptor<>(collection2.toArray()[i])).compare());
                continue;
            }
            if (i >= collection2Size) {
                diffs.addAll(new Comparator<>(new Adaptor<>(collection1.toArray()[i]), null).compare());
                continue;
            }
            diffs.addAll(new Comparator<>(new Adaptor<>(collection1.toArray()[i]), new Adaptor<>(collection2.toArray()[i])).compare());
        }
        return diffs;
    }
    
}
