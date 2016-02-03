package codeborne;

import codeborne.pattern.Pattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;

/**
 * Created by Roman_Mashchenko on 2/2/2016.
 */
@RunWith(Parameterized.class)
public class PatternUnMatchesTest {
    @Parameter()
    public String fPattern;
    @Parameter(value = 1)
    public String fInput;

    @Parameters(name = "{index}: ptrn: {0} input: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"FB", "BarBaz"},
                {"FooBar ", "FooBarBaz"},
                {"Foo*Bar", "FooBaz"},
                {"*Bar", "FooBaz"},
                {"Foo*Bar ", "FooBazBarBazBaz"},
                {"*BB", "BazFarBar"},
                {"Foo*Bar ", "FooBarBaz"},
                {" ", "FooBarBaz"}

        });
    }

    @Test
    public void test() throws Exception {
        Pattern pattern = new Pattern(fPattern);
        assertFalse(pattern.matcher(fInput).matches());
    }
}