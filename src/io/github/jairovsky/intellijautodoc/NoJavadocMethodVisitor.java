package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.annotations.NotNull;

/**
 * Visits java methods that don't have javadoc comments.
 */
class NoJavadocMethodVisitor extends JavaRecursiveElementWalkingVisitor {

    private static final Logger logger = Logger.getInstance(NoJavadocMethodVisitor.class);

    private final PsiJavaFile file;

    NoJavadocMethodVisitor(PsiJavaFile file) {
        this.file = file;
    }

    @Override
    public void visitMethod(PsiMethod method) {

        PsiDocComment comment = method.getDocComment();

        if (comment != null) {
            logger.info(comment.getText());
        } else {
            new WriteCommandAction(file.getProject()) {
                @Override
                protected void run(@NotNull Result result) {
                    PsiElementFactory factory = PsiElementFactory.SERVICE.getInstance(file.getProject());
                    PsiDocComment docCommentFromText = factory.createDocCommentFromText("/**AUTO GENERATED!!!!!!!!!11!!ONZE!!!!*/");
                    method.addBefore(docCommentFromText, method.getFirstChild());
                    CodeStyleManager.getInstance(file.getProject()).reformat(method);
                }
            }.execute();
        }
    }


}
