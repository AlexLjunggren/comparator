package com.ljunggren.comparator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.ljunggren.comparator.annotation.Comparable;
import com.ljunggren.comparator.exception.ComparatorException;
import com.ljunggren.reflectionUtils.ReflectionUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Comparator {

    private Object object1;
    private Object object2;
    
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
        if (object1.getClass() != object2.getClass()) {
            throw new ComparatorException(
                    String.format("Objects being compared must be the same class: %s compared to %s", 
                            object1.getClass().toString(), object2.getClass().toString()));
        }
        return findDiffs(object1, object2);
    }
    
    private List<Diff> findNullDiffs(Object object1, Object object2) {
        if (object1 == null) {
            List<Item> items2 = findItems(object2);
            List<Item> items1 = buildEmptyItems(items2);
            return findDiffs(items1, items2);
        }
        List<Item> items1 = findItems(object1);
        List<Item> items2 = buildEmptyItems(items1);
        return findDiffs(items1, items2);
    }
    
    private List<Item> buildEmptyItems(List<Item> items) {
        return items.stream().map(item -> 
                new Item(item.getObject(), item.getField(), null)
        ).collect(Collectors.toList());
    }
    
    private List<Diff> findDiffs(Object object1, Object object2) {
        List<Item> items1 = findItems(object1);
        List<Item> items2 = findItems(object2);
        return findDiffs(items1, items2);
    }
    
    private List<Item> findItems(Object object) {
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
        Class<?> clazz = object.getClass();
        return FieldUtils.getAllFieldsList(clazz);
    }
    
    private List<Diff> findDiffs(List<Item> items1, List<Item> items2) {
        List<Diff> diffs = new ArrayList<>();
        for (int i = 0; i < items1.size(); i++) {
            Item item1 = items1.get(i);
            Item item2 = items2.get(i);
            if (isComparable(item1)) {
                if (isEmbeddedObject(item1.getValue()) || isEmbeddedObject(item2.getValue())) {
                    diffs.addAll(new Comparator(item1.getValue(), item2.getValue()).compare());
                    continue;
                }
                Diff diff = findDiff(item1, item2);
                if (diff != null) {
                    diffs.add(diff);
                }
            }
        }
        return diffs;
    }
    
    private boolean isEmbeddedObject(Object object) {
        if (object == null) {
            return false;
        }
        Class<?> clazz = object.getClass();
        return !ReflectionUtils.isPrimitive(clazz) && !ReflectionUtils.isString(clazz);
    }
    
    private boolean isComparable(Item item) {
        return item.getField().getAnnotation(Comparable.class) != null;
    }
    
    private Diff findDiff(Item item1, Item item2) {
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
    
    private Diff buildDiff(Item item1, Item item2) {
        return Diff.builder()
                .name(item1.getField().getName())
                .value1(item1.getValue())
                .value2(item2.getValue())
                .clazz(item1.getField().getDeclaringClass())
                .build();
    }
    
}
