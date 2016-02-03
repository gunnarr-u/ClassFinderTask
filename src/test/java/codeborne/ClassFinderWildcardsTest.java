package codeborne;

import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertThat;

/**
 * Created by Roman_Mashchenko on 1/27/2016.
 */
public class ClassFinderWildcardsTest {
    @Test
    public void wildcardFromBeginning() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("*FB"),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(3), IsIterableContainingInAnyOrder.containsInAnyOrder("a.b.FooBarBaz", "c.d.FooBar", "e.f.BarFooBaz"))
        );

    }

    @Test
    public void camelCaseWildcardBetweenTwoCapitalLetters() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("F*B"),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(2), IsIterableContainingInAnyOrder.containsInAnyOrder("a.b.FooBarBaz", "c.d.FooBar"))
        );

    }


    @Test
    public void wildcardAndSpaceAtEnd() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("F* "),
                CoreMatchers.allOf(IsIterableWithSize.<String>iterableWithSize(2), IsIterableContainingInAnyOrder.containsInAnyOrder("a.b.FooBarBaz", "c.d.FooBar"))
        );

    }
    @Test
    @Ignore
    public void wildcardInMiddleOfWord() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar\ne.f.BarFooBaz".getBytes())).findMatching("*B*rBaz"),
                CoreMatchers.allOf(IsIterableContainingInAnyOrder.containsInAnyOrder("a.b.FooBarBaz"), IsIterableWithSize.<String>iterableWithSize(2))
        );

    }

    @Test
    public void findMatchingReturnsEverythingOnWildcard() throws Exception {
        assertThat(
                new ClassFinder(new ByteArrayInputStream("a.b.FooBarBaz\nc.d.FooBar".getBytes())).findMatching("*"),
                IsIterableContainingInAnyOrder.containsInAnyOrder("a.b.FooBarBaz", "c.d.FooBar")
        );

    }

}