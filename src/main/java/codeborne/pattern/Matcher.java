package codeborne.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman_Mashchenko on 1/28/2016.
 */
public class Matcher {
    int from, to, last;

    List<String> text;

    Pattern pattern;

    public Matcher(Pattern pattern, String input) {
        text = splitClassName(input);
        from = 0;
        to = text.size();
        last = 0;
        this.pattern = pattern;
    }

    private List<String> splitClassName(String className) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : className.toCharArray()) {
            if(Character.isUpperCase(c)  && sb.length() > 0) {
                tokens.add(sb.toString().toLowerCase());
                sb = new StringBuilder();
            }
            sb.append(c);
        }
        if(sb.length() > 0) {
            tokens.add(sb.toString().toLowerCase());
        }
        return tokens;
    }

    public boolean matches() {
        return pattern.root.match(this, from, text);
    }
}