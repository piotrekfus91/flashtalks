package com.github.flashtalks.hashmap;

public class NonComparableClass {
    protected Long value;
    protected Integer hashDivisor;

    public NonComparableClass(Long value, Integer hashDivisor) {
        this.value = value;
        this.hashDivisor = hashDivisor;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof NonComparableClass)) {
            return false;
        }

        NonComparableClass that = (NonComparableClass) obj;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return (int) (value % hashDivisor);
    }
}
