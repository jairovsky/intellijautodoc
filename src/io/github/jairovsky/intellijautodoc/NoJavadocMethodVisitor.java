package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.javadoc.PsiDocComment;
import io.github.jairovsky.intellijautodoc.text.MethodNameSplitter;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Visits java methods that don't have javadoc comments.
 */
class NoJavadocMethodVisitor extends JavaRecursiveElementWalkingVisitor {

    private static final Logger logger = Logger.getInstance(NoJavadocMethodVisitor.class);

    private final PsiJavaFile file;
    private final MethodNameSplitter methodNameSplitter;
    private final PsiElementFactory elementFactory;
    private final CodeStyleManager codeStyleManager;

    NoJavadocMethodVisitor(PsiJavaFile file) {
        this.file = file;

        this.methodNameSplitter = ServiceManager.getService(MethodNameSplitter.class);
        this.elementFactory = PsiElementFactory.SERVICE.getInstance(file.getProject());
        this.codeStyleManager = CodeStyleManager.getInstance(file.getProject());
    }

    @Override
    public void visitMethod(PsiMethod method) {

        PsiDocComment comment = method.getDocComment();

        if (comment == null) {
            new WriteCommandAction(file.getProject()) {
                @Override
                protected void run(@NotNull Result result) {

                    List<String> words = methodNameSplitter.splitWords(method.getName());

                    PsiDocComment docComment = 
                            elementFactory.createDocCommentFromText(wrapInJavadocMarkup(joinSentence(words)));

                    method.addBefore(docComment, method.getFirstChild());
                    
                    codeStyleManager.reformat(method);
                }
            }.execute();
        }
    }

    private String joinSentence(List<String> words) {

        return StringUtils.join(words, " ");
    }

    private String wrapInJavadocMarkup(String str) {
        return String.format("/**%s*/", str);
    }

}
