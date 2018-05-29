package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

public class InsertJavadocAction extends HideableAction {

    @Override
    public void actionPerformed(AnActionEvent event) {

        PsiJavaFile file = (PsiJavaFile) event.getData(LangDataKeys.PSI_FILE);

        if (file != null) {
            UndocumentedMethodVisitor methodVisitor = new UndocumentedMethodVisitor(file);
            UndocumentedClassVisitor classVisitor = new UndocumentedClassVisitor(file);

            new BatchWriteAction(file.getProject(), classVisitor, methodVisitor).execute();
        }
    }

    @Override
    Boolean isVisible(AnActionEvent event) {
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
        Caret caret = event.getData(LangDataKeys.CARET);

        return psiFile != null &&
                PsiJavaFile.class.isAssignableFrom(psiFile.getClass()) &&
                caret != null;
    }
}
