package io.github.jairovsky.intellijautodoc.text.sentences;

import java.util.List;

import static java.lang.String.format;

class SentenceAssemblerForClasses implements SentenceAssembler {

    @Override
    public String assembleSentence(List<String> words) {
        return format("The class %s.", words.get(0));
    }
}
