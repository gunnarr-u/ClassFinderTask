package codeborne;

import codeborne.pattern.Pattern;

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
            Pattern pattern1 = new Pattern(pattern);
            return br.lines().filter(s-> pattern1.matches(s.substring(s.lastIndexOf(".") + 1).trim())).sorted(comparing(s -> s.substring(s.lastIndexOf(".") + 1))).collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
