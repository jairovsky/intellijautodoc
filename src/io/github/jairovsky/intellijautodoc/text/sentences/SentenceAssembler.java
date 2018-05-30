package io.github.jairovsky.intellijautodoc.text.sentences;

import java.util.List;

public interface SentenceAssembler {

    String assembleSentence(List<String> words);

    enum Type {
        CLASS, METHOD, FIELD
    }
}
