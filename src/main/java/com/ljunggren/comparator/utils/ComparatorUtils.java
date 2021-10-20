package com.ljunggren.comparator.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.ljunggren.comparator.Item;
import com.ljunggren.comparator.exception.ComparatorException;

public class ComparatorUtils<T> {

    public List<Item> findItems(T object) {
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
    
    private List<Field> findObjectFields(T object) {
        Class<?> clazz = object.getClass();
        return FieldUtils.getAllFieldsList(clazz);
    }
    
}
