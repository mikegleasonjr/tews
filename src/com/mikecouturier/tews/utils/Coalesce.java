package com.mikecouturier.tews.utils;

// credit
// http://www.togsblom.com/2011/11/since-java-doesnt-have-null-coalescing.html

public class Coalesce {
    public static <T> T coalesce(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }
}
