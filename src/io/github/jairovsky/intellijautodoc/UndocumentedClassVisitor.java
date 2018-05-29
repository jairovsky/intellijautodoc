package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import org.fest.util.Lists;

import java.util.List;

/**
 * Visits java classes that don't have javadoc comments.
 */
class UndocumentedClassVisitor extends JavaRecursiveElementWalkingVisitor implements SimpleAction {

    private static final Logger logger = Logger.getInstance(UndocumentedClassVisitor.class);

    private final PsiJavaFile file;
    private final List<PsiClass> undocumentedClasses;

    UndocumentedClassVisitor(PsiJavaFile file) {
        this.file = file;
        this.undocumentedClasses = Lists.newArrayList();
    }

    @Override
    public void visitClass(PsiClass clazz) {

        super.visitClass(clazz);

        PsiDocComment comment = clazz.getDocComment();

        if (comment == null) {

            logger.debug("adding class {} to the list of undocumented classes");

            undocumentedClasses.add(clazz);
        }
    }

    @Override
    public void execute() {

        file.accept(this);
        new WriteClassJavadocs(file.getProject(), undocumentedClasses).execute();
    }
}
