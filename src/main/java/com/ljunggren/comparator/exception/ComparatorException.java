package com.ljunggren.comparator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ComparatorException extends Exception {

    private static final long serialVersionUID = -8650705158644493554L;

    public ComparatorException(String errorMessage) {
        super(errorMessage);
    }
    
}
