package com.ljunggren.comparator;

import com.ljunggren.comparator.annotation.Comparable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Adaptor<T> {

    @Comparable
    private T object;
    
}
