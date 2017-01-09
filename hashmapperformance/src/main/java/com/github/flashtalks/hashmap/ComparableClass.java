package com.github.flashtalks.hashmap;

public class ComparableClass extends NonComparableClass implements Comparable {
    public ComparableClass(Long value, Integer hashDivisor) {
        super(value, hashDivisor);
    }

    public int compareTo(Object o) {
        ComparableClass that = (ComparableClass) o;
        return value.compareTo(that.value);
    }
}
