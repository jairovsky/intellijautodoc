package io.github.jairovsky.intellijautodoc.javadoc;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.javadoc.PsiDocComment;
import io.github.jairovsky.intellijautodoc.project.SimpleAction;
import io.github.jairovsky.intellijautodoc.text.NameSplitter;
import org.apache.commons.lang.StringUtils;

import java.util.List;

class WriteMethodJavadocs implements SimpleAction {

    private final List<PsiMethod> methodsToWrite;
    private final NameSplitter nameSplitter;
    private final PsiElementFactory elementFactory;
    private final CodeStyleManager codeStyleManager;

    WriteMethodJavadocs(Project project, List<PsiMethod> methodsToWrite) {

        this.methodsToWrite = methodsToWrite;
        this.nameSplitter = ServiceManager.getService(NameSplitter.class);
        this.elementFactory = PsiElementFactory.SERVICE.getInstance(project);
        this.codeStyleManager = CodeStyleManager.getInstance(project);
    }

    @Override
    public void execute() {

        methodsToWrite.forEach(x -> {

            List<String> words =
                    nameSplitter.splitWords(x.getName());

            PsiDocComment docComment =
                    elementFactory.createDocCommentFromText(wrapInJavadocMarkup(joinSentence(words)));

            x.addBefore(docComment, x.getFirstChild());

            codeStyleManager.reformat(x);
        });
    }

    private String joinSentence(List<String> words) {

        return StringUtils.join(words, " ");
    }

    private String wrapInJavadocMarkup(String str) {
        return String.format("/**%s*/", str);
    }
}
