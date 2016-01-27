package codeborne;

import org.junit.Test;

/**
 * Created by Roman_Mashchenko on 1/27/2016.
 */
public class PatternMatcherTest {

    @Test
    public void testSplitPatternRecurcive() throws Exception {
        new PatternMatcher("").splitPattern("*");
    }
}