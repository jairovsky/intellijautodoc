package io.github.jairovsky.intellijautodoc.javadoc;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.javadoc.PsiDocComment;
import io.github.jairovsky.intellijautodoc.project.SimpleAction;
import io.github.jairovsky.intellijautodoc.text.sentences.SentenceAssembler;
import io.github.jairovsky.intellijautodoc.text.sentences.SentenceAssemblerFactory;

import java.util.List;

import static org.fest.util.Lists.newArrayList;

public class WriteClassJavadocs implements SimpleAction {

    private final List<PsiClass> classesToWrite;
    private final PsiElementFactory elementFactory;
    private final CodeStyleManager codeStyleManager;
    private final SentenceAssembler sentenceAssembler;

    public WriteClassJavadocs(Project project, List<PsiClass> classesToWrite) {
        this.classesToWrite = classesToWrite;
        this.elementFactory = PsiElementFactory.SERVICE.getInstance(project);
        this.codeStyleManager = CodeStyleManager.getInstance(project);
        this.sentenceAssembler = SentenceAssemblerFactory.newAssembler(SentenceAssembler.Type.CLASS);
    }

    @Override
    public void execute() {

        classesToWrite.forEach(this::createDocumentationForClass);
    }

    private void createDocumentationForClass(PsiClass clazz) {

        List<String> words =
                newArrayList(clazz.getName());

        String sentence =
                sentenceAssembler.assembleSentence(words);

        String javadocMarkup =
                wrapInJavadocMarkup(sentence);

        PsiDocComment docComment =
                elementFactory.createDocCommentFromText(javadocMarkup);

        clazz.addBefore(docComment, clazz.getFirstChild());

        codeStyleManager.reformat(clazz);
    }

    private String wrapInJavadocMarkup(String str) {
        return String.format("/**%s*/", str);
    }
}
