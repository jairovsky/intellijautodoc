package io.github.jairovsky.intellijautodoc.text.sentences;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class SentenceAssemblerForMethods implements SentenceAssembler {

    @Override
    public String assembleSentence(List<String> words) {

        if (words == null || words.size() == 0) {
            throw new IllegalArgumentException("Empty list of words");
        }

        List<String> lowercasedWords =
                lowercaseListOfWords(words);

        String joined =
                joinWordsWithSpaces(lowercasedWords);

        String capitalized =
                capitalizeFirstChar(joined);

        String punctuated =
                punctuateSentence(capitalized);

        return punctuated;
    }

    private List<String> lowercaseListOfWords(List<String> words) {

        return words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    private String joinWordsWithSpaces(List<String> words) {

        StringJoiner joiner = new StringJoiner(" ");

        words.forEach(joiner::add);

        return joiner.toString();
    }

    private String capitalizeFirstChar(String str) {

        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);

        return String.valueOf(chars);
    }

    private String punctuateSentence(String capitalized) {

        return capitalized + ".";
    }
}
