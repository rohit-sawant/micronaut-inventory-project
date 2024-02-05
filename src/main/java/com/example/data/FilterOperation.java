package com.example.data;

import com.example.exception.InvalidFilterOperation;

import java.util.logging.Filter;

public enum FilterOperation {
    AND,

    OR;

    public static FilterOperation getValue(String value) throws InvalidFilterOperation {
        if(FilterOperation.AND.toString().equalsIgnoreCase(value)){
            return FilterOperation.AND;
        }
        else if(FilterOperation.OR.toString().equalsIgnoreCase(value)){
            return FilterOperation.OR;
        }
        else{
            throw new InvalidFilterOperation("Invalid Filter Operation value");
        }
    }



}
