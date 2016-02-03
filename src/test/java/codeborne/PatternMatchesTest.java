package codeborne;

import codeborne.pattern.Pattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Roman_Mashchenko on 2/2/2016.
 */
@RunWith(Parameterized.class)
public class PatternMatchesTest {
    @Parameters(name = "{index}: ptrn: {0} input: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"FB", "FooBar"},
                {"*", "FooBar"},
                {"FooBar", "FooBar"},
                {"FooBar", "FooBarBaz"},
                {"Foo*Bar", "FooBar"},
                {"Foo*Bar", "FooBazBar"},
                {"Foo*Bar", "FooBazBazBazBar"},
                {"FBar", "FooBar"},
                {"*FBar", "BazFooBar"},
                {"*B*B", "BazFooBar"},
                {"*BB", "BazBarBar"},
                {"*BaB", "BazBarBar"},
                {"*FooBarBaz", "FooBarBazFooBar"}


        });
}
    @Parameter()
    public String fPattern;

    @Parameter(value = 1)
    public String fInput;


    @Test
    public void test() throws Exception {
        Pattern pattern = new Pattern(fPattern);
        assertTrue(pattern.matcher(fInput).matches());
    }
}