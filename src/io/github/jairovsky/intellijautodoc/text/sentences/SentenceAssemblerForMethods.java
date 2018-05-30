package io.github.jairovsky.intellijautodoc.text.sentences;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class SentenceAssemblerForMethods implements SentenceAssembler {

    @Override
    public String assembleSentence(List<String> words) {

        List<String> lowercasedWords =
                lowercaseListOfWords(words);

        String joined =
                joinWordsWithSpaces(lowercasedWords);

        String capitalized =
                capitalizeFirstChar(joined);

        return joined;
    }

    private String capitalizeFirstChar(String str) {


    }

    private String joinWordsWithSpaces(List<String> words) {

        StringJoiner joiner = new StringJoiner(" ");

        words.forEach(joiner::add);

        return joiner.toString();
    }

    private List<String> lowercaseListOfWords(List<String> words) {

        return words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
