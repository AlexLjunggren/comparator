package io.ljunggren.comparator;

import io.ljunggren.comparator.annotation.Comparable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Adaptor<T> {

    @Comparable
    private T object;
    
}
