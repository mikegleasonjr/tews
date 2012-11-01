package com.mikecouturier.tews.utils;

import org.junit.Test;

import static com.mikecouturier.tews.utils.Coalesce.coalesce;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

// credit
// http://www.togsblom.com/2011/11/since-java-doesnt-have-null-coalescing.html

public class CoalesceTest {
    @Test
    public void shouldReturnValueIfNotNull()
    {
        Double theNonNullValue = 123d;
        Double theDefaultValue = 1d;
        Double returned = coalesce(theNonNullValue, theDefaultValue);
        assertThat(theNonNullValue, is(returned));
    }

    @Test
    public void shouldReturnDefaultValueIfValueIsNull()
    {
        Double theNullValue = null;
        Double theDefaultValue = 1d;
        Double returned = coalesce(theNullValue, theDefaultValue);
        assertThat(theDefaultValue, is(returned));
    }

    @Test
    public void shouldReturnNullIfBothArgumentsNull()
    {
        Double theNonNullValue = null;
        Double theDefaultValue = null;
        Double theResult = coalesce(theNonNullValue, theDefaultValue);
        assertThat(theResult, is(nullValue()));
    }
}
