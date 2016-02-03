package codeborne.pattern;

import java.util.List;
import java.util.Objects;

/**
 * Created by Roman_Mashchenko on 2/2/2016.
 */
public class Pattern {

    private static final Node accept = new Node();
    Node root = new Root();
    private StringBuilder sb;


    public Pattern(String pattern) {
        compile(pattern);
    }

    public Matcher matcher(String input) {
        return new Matcher(this, input);
    }

    public boolean matches(String input) {
        Matcher m = new Matcher(this, input);
        return m.matches();
    }

    private void compile(String pattern) {
        char[] tmp = pattern.toCharArray();
        Node tail = root;
        sb = new StringBuilder();

        for (int i = 0; ; i++) {
            final char c = tmp[i];
            switch (c) {
                case '*': {
                    tail = appendTail(tail, new GreedyRepeatable(new All()));
                    break;
                }
                case ' ': {
                    appendTail(tail, new End());
                    return;
                }
                default: {
                    if (Character.isUpperCase(c) && sb.length() > 0) {
                        tail = appendTail(tail);
                        sb.append(c);
                    } else {
                        sb.append(c);
                    }
                }
            }
            if (i + 1 == tmp.length) {
                tail = appendTail(tail);
                tail.next = new LastNode();
                return;
            }
        }
    }

    private Node appendTail(Node tail, Node next) {
        Objects.requireNonNull(tail);
        Objects.requireNonNull(next);
        tail = appendTail(tail);
        tail.next = next;
        tail = tail.next;
        return tail;
    }

    private Node appendTail(Node tail) {
        if (sb.length() > 0) {
            tail.next = new StartsWith(sb.toString().toLowerCase());
            tail = tail.next;
            sb = new StringBuilder();
        }
        return tail;
    }

    static class Node {
        Node next;

        Node() {
            next = Pattern.accept;
        }

        boolean match(Matcher matcher, int i, List<String> seq) {
            matcher.last = i;
            return true;
        }
    }

    private class StartsWith extends Node {
        private String prefix;

        StartsWith(String prefix) {
            this.prefix = prefix;
        }

        boolean match(Matcher matcher, int i, List<String> seq) {
            matcher.last = i;
            return (i < matcher.to && seq.get(i).startsWith(prefix)) && next.match(matcher, i + 1, seq);
        }
    }


    private class LastNode extends Node {
        boolean match(Matcher matcher, int i, List<String> seq) {
            matcher.last = i;
            return true;
        }
    }

    private class All extends Node {
        boolean match(Matcher matcher, int i, List<String> seq) {
            return i < matcher.to && next.match(matcher, ++i, seq);
        }
    }

    private class GreedyRepeatable extends Node {
        Node atom;

        public GreedyRepeatable(Node atom) {
            this.atom = atom;
        }

        @Override
        boolean match(Matcher matcher, int i, List<String> seq) {
            int foundIndex = -1;
            while(atom.match(matcher, i, seq)) {
                if (matcher.last == i) break;
                if (next.match(matcher, i, seq)) {
                    foundIndex = i;
                }
                i++;
            }
            return next.match(matcher, foundIndex > -1? foundIndex: matcher.last, seq);
        }
    }

    private class End extends Node {
        boolean match(Matcher matcher, int i, List<String> seq) {
            int endIndex = matcher.text.size();
            return i == endIndex && next.match(matcher, i, seq);
        }
    }

    private class Root extends Node {
        @Override
        boolean match(Matcher matcher, int i, List<String> seq) {
            return next.match(matcher, i, seq);
        }
    }
}
