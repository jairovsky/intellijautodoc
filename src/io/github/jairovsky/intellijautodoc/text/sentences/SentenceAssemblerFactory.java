package io.github.jairovsky.intellijautodoc.text.sentences;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

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

    public static SentenceAssembler newAssembler(PsiElement elem) {

        if (PsiClass.class.isAssignableFrom(elem.getClass())) {
            return assemblerForClasses((PsiClass) elem);
        }

        throw new IllegalArgumentException("Can't find a sentence assembler for " + elem.getClass());
    }

    private static SentenceAssembler assemblerForClasses(PsiClass elem) {

        if (elem.isInterface()) {
            return new SentenceAssemblerForInterfaces();
        }

        if (elem.isEnum()) {
            return new SentenceAssemblerForEnums();
        }

        if (elem.isAnnotationType()) {
            return new SentenceAssemblerForAnnotations();
        }

        return new SentenceAssemblerForClasses();
    }

}
