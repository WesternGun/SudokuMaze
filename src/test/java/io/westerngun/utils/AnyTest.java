package io.westerngun.utils;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class AnyTest {
    @Test
    public void testAnything() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.remove(new Integer(1));
        assertThat(numbers.size(), is(1));
        assertThat(numbers.get(0), is(2));
    }
}
