package codeborne;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * Created by Roman_Mashchenko on 1/27/2016.
 */
class PatternMatcher {

    private final List<Predicate<String>> patternSeparated;
    private boolean wildcardMet = false;
    private boolean matches = false;

    public PatternMatcher(String pattern) {

        patternSeparated = splitPattern(pattern);
    }

    public List<Predicate<String>> splitPattern(String pattern) {
        List<Predicate<String>> tokens = new ArrayList<>();
        splitPatternRecurcive( pattern,  tokens);
        return tokens;
    }

    private void splitPatternRecurcive(String pattern, List<Predicate<String>> tokens) {
        StringBuilder sb = new StringBuilder();
        final char[] chars = pattern.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((Character.isUpperCase(c) || c == '*' || c == ' ' || i == chars.length-1) && sb.length() > 0) {
                if (i == chars.length-1) sb.append(c);
                final String token = sb.toString().toLowerCase();
                tokens.add(token::startsWith);
                if (c == '*') tokens.add(s -> true);
                if (c == ' ') tokens.add(s -> s == null);
                splitPatternRecurcive(pattern.substring(i), tokens);
                return;
            }
            sb.append(c);
        }
    }

    private List<String> splitClassName(String className) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : className.substring(className.lastIndexOf('.') + 1).toCharArray()) {
            if (Character.isUpperCase(c) && sb.length() > 0) {
                tokens.add(sb.toString().toLowerCase());
                sb = new StringBuilder();
            }
            sb.append(c);
        }
        if (sb.length() > 0) {
            tokens.add(sb.toString().toLowerCase());
        }
        return tokens;
    }

    public boolean matches(String className) {
        Queue<String> clazz = new ArrayDeque<>(splitClassName(className));
        wildcardMet = false;
        matches = false;
        for (Predicate<String> ptrn : patternSeparated) {

        }
        return wildcardMet || matches;
    }

    private boolean accept(String token, String ptrn) {
        return false;
    }

    private boolean reject(String token, String ptrn) {
        return false;
    }
}
