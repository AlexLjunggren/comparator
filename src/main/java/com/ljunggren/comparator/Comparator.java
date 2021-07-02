package com.ljunggren.comparator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.ljunggren.comparator.annotation.Comparable;
import com.ljunggren.comparator.exception.ComparatorException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Comparator {

    private Object object1;
    private Object object2;
    
    public boolean isEqual() throws ComparatorException {
        return compare().isEmpty();
    }
    
    public List<Diff> compare() throws ComparatorException {
        if (object1.getClass() != object2.getClass()) {
            throw new ComparatorException(
                    String.format("Objects being compared must be the same class: %s compared to %s", 
                            object1.getClass().toString(), object2.getClass().toString()));
        }
        List<Item> items1 = findItems(object1);
        List<Item> items2 = findItems(object2);
        return findDiffs(items1, items2);
    }
    
    private List<Item> findItems(Object object) throws ComparatorException {
        List<Item> items = new ArrayList<Item>();
        List<Field> fields = findObjectFields(object);
        for (Field field : fields) {
            try {
                Object value = FieldUtils.readField(field, object, true);
                items.add(new Item(object, field, value));
            } catch (IllegalAccessException e) {
                throw new ComparatorException(e.getMessage());
            }
        }
        return items;
    }
    
    private List<Field> findObjectFields(Object object) {
        if (object != null) {
            Class<?> clazz = object.getClass();
            Field[] fields = FieldUtils.getAllFields(clazz);
            return Arrays.asList(fields);
        }
        return new ArrayList<Field>();
    }
    
    private List<Diff> findDiffs(List<Item> items1, List<Item> items2) {
        List<Diff> diffs = new ArrayList<>();
        for (int i = 0; i < items1.size(); i++) {
            if (isCompatable(items1.get(i))) {
                Diff diff = findDiff(items1.get(i), items2.get(i));
                if (diff != null) {
                    diffs.add(diff);
                }
            }
        }
        return diffs;
    }
    
    private boolean isCompatable(Item item) {
        return item.getField().getAnnotation(Comparable.class) != null;
    }
    
    private Diff findDiff(Item item1, Item item2) {
        if (!item1.getValue().equals(item2.getValue())) {
            return Diff.builder()
                    .name(item1.getField().getName())
                    .value1(item1.getValue())
                    .value2(item2.getValue())
                    .build();
        }
        return null;
    }
    
}
