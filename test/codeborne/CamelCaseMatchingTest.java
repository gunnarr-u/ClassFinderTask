package codeborne;

import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertThat;

/**
 * Created by Roman_Mashchenko on 1/27/2016.
 */
public class CamelCaseMatchingTest {

    @Test
    public void camelCaseMatching() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("FB"),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(2), IsIterableContainingInAnyOrder.containsInAnyOrder("a.b.FooBarBaz", "c.d.FooBar"))
        );

    }

    @Test
    public void camelCaseMatchingWithSpaceEnd() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("F*Bar "),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(1), IsIterableContainingInAnyOrder.containsInAnyOrder("c.d.FooBar"))
        );

    }

    @Test
    public void camelCaseMatchingWithSpaceInTheMiddle() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("F*Bar Asdasdasd"),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(1), IsIterableContainingInAnyOrder.containsInAnyOrder("c.d.FooBar"))
        );

    }

    @Test
    public void simpleCamelCase() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("BFB"),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(1), IsIterableContainingInAnyOrder.containsInAnyOrder("e.f.BarFooBaz"))
        );

    }

    @Test
    public void firstMathingOnSecondWord() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("BB"),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(1), IsIterableContainingInAnyOrder.containsInAnyOrder("a.b.FooBarBaz"))
        );

    }
}