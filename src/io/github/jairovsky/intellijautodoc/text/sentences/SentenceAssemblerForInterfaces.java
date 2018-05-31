package io.github.jairovsky.intellijautodoc.text.sentences;

import java.util.List;

public class SentenceAssemblerForInterfaces implements SentenceAssembler {

    @Override
    public String assembleSentence(List<String> words) {
        return String.format("The interface %s.", words.get(0));
    }
}
