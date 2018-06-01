package io.github.jairovsky.intellijautodoc.text.sentences;

import java.util.List;

public class SentenceAssemblerForAnnotations implements SentenceAssembler {

    @Override
    public String assembleSentence(List<String> words) {
        return String.format("The annotation %s.", words.get(0));
    }
}
