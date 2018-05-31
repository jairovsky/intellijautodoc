package io.github.jairovsky.intellijautodoc.text.sentences;

public class SentenceAssemblerFactory {

    public static SentenceAssembler newAssembler(SentenceAssembler.Type type) {

        switch (type) {
            case CLASS:
                return new SentenceAssemblerForClasses();
            case METHOD:
                return new SentenceAssemblerForMethods();
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}
