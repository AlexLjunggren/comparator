package com.ljunggren.comparator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Diff {

    private String name;
    private Object value1;
    private Object value2;
    private Class<?> clazz;
    
}
