package codeborne;

import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Roman_Mashchenko on 1/25/2016.
 */
public class ClassFinderTest {

    @Test
    public void findMatchingReturnsNonNull() throws Exception {
        assertNotNull(new ClassFinder(new ByteArrayInputStream(new byte[0])).findMatching(null));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNPEIfNullInput() throws Exception {
        new ClassFinder(null);
    }

    @Test(expected = UncheckedIOException.class)
    public void shouldThrowUIOEIfBrokenInput() throws Exception {
        InputStream classNamesStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
        new ClassFinder(classNamesStream).findMatching("*");
    }


    @Test
    public void findMatchingReturnsFullClassNameMatching() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar".getBytes())).findMatching("FooBarBaz"),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(1), IsIterableContainingInAnyOrder.containsInAnyOrder("a.b.FooBarBaz"))
        );

    }






    @Test
    public void shouldReturnOrdered() throws Exception {
        List<String> input = Arrays.asList("a.b.FooBarBaz", "c.d.FooBar", "e.f.BarFooBaz");
        Collections.shuffle(input);
        assertThat(
                new ClassFinder(new ByteArrayInputStream(input.stream().collect(Collectors.joining("\n")).getBytes())).findMatching("*"),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(3), IsIterableContainingInOrder.contains("e.f.BarFooBaz", "c.d.FooBar", "a.b.FooBarBaz"))
        );

    }

}