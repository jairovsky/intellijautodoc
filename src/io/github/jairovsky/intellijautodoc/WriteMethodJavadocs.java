package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.javadoc.PsiDocComment;
import io.github.jairovsky.intellijautodoc.text.MethodNameSplitter;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

class WriteMethodJavadocs extends WriteCommandAction {

    private static final String ACTION_NAME = "Insert javadoc";

    private final List<PsiMethod> methodsToWrite;
    private final MethodNameSplitter methodNameSplitter;
    private final PsiElementFactory elementFactory;
    private final CodeStyleManager codeStyleManager;

    WriteMethodJavadocs(@Nullable Project project, List<PsiMethod> methodsToWrite) {

        super(project, ACTION_NAME);

        this.methodsToWrite = methodsToWrite;
        this.methodNameSplitter = ServiceManager.getService(MethodNameSplitter.class);
        this.elementFactory = PsiElementFactory.SERVICE.getInstance(project);
        this.codeStyleManager = CodeStyleManager.getInstance(project);
    }

    @Override
    protected void run(@NotNull Result result) {

        methodsToWrite.forEach(x -> {

            List<String> words =
                    methodNameSplitter.splitWords(x.getName());

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
