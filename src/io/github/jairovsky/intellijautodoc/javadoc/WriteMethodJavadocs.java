package io.github.jairovsky.intellijautodoc.javadoc;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.javadoc.PsiDocComment;
import io.github.jairovsky.intellijautodoc.action.SimpleAction;
import io.github.jairovsky.intellijautodoc.text.NameSplitter;
import io.github.jairovsky.intellijautodoc.text.sentences.SentenceAssembler;
import io.github.jairovsky.intellijautodoc.text.sentences.SentenceAssemblerFactory;
import org.fest.util.Lists;

import java.util.List;

class WriteMethodJavadocs implements SimpleAction {

    private final List<PsiMethod> methodsToWrite;
    private final NameSplitter nameSplitter;
    private final PsiElementFactory elementFactory;
    private final CodeStyleManager codeStyleManager;
    private final SentenceAssembler sentenceAssembler;

    WriteMethodJavadocs(Project project, List<PsiMethod> methodsToWrite) {

        this.methodsToWrite = methodsToWrite;
        this.nameSplitter = ServiceManager.getService(NameSplitter.class);
        this.elementFactory = PsiElementFactory.SERVICE.getInstance(project);
        this.codeStyleManager = CodeStyleManager.getInstance(project);
        this.sentenceAssembler = SentenceAssemblerFactory.newAssembler(SentenceAssembler.Type.METHOD);
    }

    @Override
    public void execute() {

        methodsToWrite.forEach(this::createDocumentationForMethod);
    }

    private void createDocumentationForMethod(PsiMethod method) {

        List<String> words =
                nameSplitter.splitWords(method.getName());

        String sentence =
                sentenceAssembler.assembleSentence(words);

        for (PsiParameter param: method.getParameterList().getParameters()) {
            sentence += String.format("\n@param %s - the %s", param.getName(), sentenceAssembler.assembleSentence(nameSplitter.splitWords(param.getName())));
        }

        if (!method.getReturnType().equalsToText("void")) {
            sentence += String.format("\n@return the %s", method.getReturnType().getPresentableText());
        }

        String javadocMarkup =
                wrapInJavadocMarkup(sentence);

        PsiDocComment docComment =
                elementFactory.createDocCommentFromText(javadocMarkup);

        method.addBefore(docComment, method.getFirstChild());

        codeStyleManager.reformat(method);
    }

    private String wrapInJavadocMarkup(String str) {
        return String.format("/**%s*/", str);
    }
}
