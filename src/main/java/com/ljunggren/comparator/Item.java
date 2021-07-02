package com.ljunggren.comparator;

import java.lang.reflect.Field;

import lombok.Getter;

@Getter
public class Item {

    private Object object;
    private Field field;
    private Object value;

    public Item(Object object, Field field, Object value) {
        this.object = object;
        this.field = field;
        this.value = value;
    }
    
    public void setValue(Object value) {
        try {
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO: Add something here
        }
    }
    
}
