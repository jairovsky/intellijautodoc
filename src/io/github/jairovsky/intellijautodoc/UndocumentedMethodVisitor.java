package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import org.fest.util.Lists;

import java.util.List;

import static java.lang.String.format;

/**
 * Visits java methods that don't have javadoc comments.
 */
class UndocumentedMethodVisitor extends JavaRecursiveElementWalkingVisitor implements SimpleAction {

    private static final Logger logger = Logger.getInstance(UndocumentedMethodVisitor.class);

    private final PsiJavaFile file;
    private final List<PsiMethod> undocumentedMethods;

    UndocumentedMethodVisitor(PsiJavaFile file) {
        this.file = file;
        this.undocumentedMethods = Lists.newArrayList();
    }

    @Override
    public void visitMethod(PsiMethod method) {

        super.visitMethod(method);

        PsiDocComment comment = method.getDocComment();

        if (comment == null) {

            logger.debug(format("adding method %s to list of undocumented methods", method.getName()));

            undocumentedMethods.add(method);
        }
    }

    @Override
    public void execute() {

        file.accept(this);
        new WriteMethodJavadocs(file.getProject(), undocumentedMethods).execute();
    }
}
