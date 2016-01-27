package codeborne;

import java.io.*;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by Roman_Mashchenko on 1/25/2016.
 */
public class ClassFinder {

    private final InputStream is;

    public ClassFinder(InputStream classNamesStream) {
        Objects.requireNonNull(classNamesStream);
        is = classNamesStream;
    }

    public Collection<String> findMatching(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            return Collections.emptyList();
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            PatternMatcher matcher = new PatternMatcher(pattern);
            return br.lines().filter(matcher::matches).sorted(comparing(s -> s.substring(s.lastIndexOf(".") + 1))).collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private class PatternMatcher {

        private final List<String> patternSeparated;

        public PatternMatcher(String pattern) {

            patternSeparated = splitPattern(pattern);
        }

        private List<String> splitPattern(String pattern) {
            List<String> tokens = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (char c : pattern.toCharArray()) {
                if((Character.isUpperCase(c) || c == '*') && sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb = new StringBuilder();
                } else if ( c == ' ') {
                    if(sb.length() > 0) {
                        tokens.add(sb.toString());
                    }
                    tokens.add(Character.toString(c));
                    break;
                }
                sb.append(c);
            }
            if(sb.length() > 0) {
                tokens.add(sb.toString());
            }
            return tokens;
        }

        private List<String> splitClassName(String className) {
            List<String> tokens = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (char c : className.substring(className.lastIndexOf('.') + 1).toCharArray()) {
                if(Character.isUpperCase(c)  && sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb = new StringBuilder();
                }
                sb.append(c);
            }
            if(sb.length() > 0) {
                tokens.add(sb.toString());
            }
            return tokens;
        }

        public boolean matches(String className) {
            Queue<String> clazz = new ArrayDeque<>(splitClassName(className));
            boolean wildcardMet = false;
            boolean matches = false;
            for (String ptrn : patternSeparated) {
                if ("*".equals(ptrn)) {
                    wildcardMet = true;
                    matches = false;
                    continue;
                }
                if (" ".equals(ptrn) && (wildcardMet || clazz.peek() == null)) {
                    return true;
                } else if (" ".equals(ptrn) && clazz.peek() != null) {
                    return false;
                }

                if (wildcardMet) {
                    while (clazz.peek() != null && !matches) {
                        matches = clazz.poll().startsWith(ptrn);
                    }
                    wildcardMet = false;
                } else if (clazz.peek() != null && clazz.poll().startsWith(ptrn)) {
                    matches = true;
                    wildcardMet = false;
                } else {
                    matches = false;
                }
                if (!matches) return false;
            }
            return wildcardMet || matches;
        }
    }

}
