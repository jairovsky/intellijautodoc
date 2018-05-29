package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import org.fest.util.Lists;

import java.util.List;

/**
 * Visits java methods that don't have javadoc comments.
 */
class UndocumentedMethodVisitor extends JavaRecursiveElementWalkingVisitor {

    private static final Logger logger = Logger.getInstance(UndocumentedMethodVisitor.class);

    private final PsiJavaFile file;
    private final List<PsiMethod> undocumentedMethods;

    UndocumentedMethodVisitor(PsiJavaFile file) {
        this.file = file;
        this.undocumentedMethods = Lists.newArrayList();
    }

    @Override
    public void visitMethod(PsiMethod method) {

        PsiDocComment comment = method.getDocComment();

        if (comment == null) {

            logger.debug("adding method {} to list of undocumented methodsToWrite", method.getName());

            undocumentedMethods.add(method);
        }
    }

    public void execute() {

        file.accept(this);
        new WriteMethodJavadocs(file.getProject(), undocumentedMethods).execute();
    }
}
