package io.github.jairovsky.intellijautodoc.text;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CamelCaseSplitter implements NameSplitter {

    private static final Pattern CAMEL_CASE =
            Pattern.compile("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

    @Override
    public List<String> splitWords(String methodName) {

        String[] words = CAMEL_CASE.split(methodName);

        return Arrays
                .stream(words)
                .collect(Collectors.toList());
    }
}
