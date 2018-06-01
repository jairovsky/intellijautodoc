package io.github.jairovsky.intellijautodoc.javadoc;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import io.github.jairovsky.intellijautodoc.project.BatchWriteAction;
import io.github.jairovsky.intellijautodoc.project.HideableAction;

public class InsertJavadocAction extends HideableAction {

    private static final String ACTION_NAME = "Insert javadocs";

    @Override
    public void actionPerformed(AnActionEvent event) {

        PsiJavaFile file =
                (PsiJavaFile) event.getData(LangDataKeys.PSI_FILE);

        if (file != null) {

            UndocumentedMethodVisitor methodVisitor =
                    new UndocumentedMethodVisitor(file);

            UndocumentedClassVisitor classVisitor =
                    new UndocumentedClassVisitor(file);

            BatchWriteAction batchWriteAction =
                    new BatchWriteAction(
                            file.getProject(),
                            ACTION_NAME,
                            classVisitor,
                            methodVisitor);

            batchWriteAction.execute();
        }
    }

    @Override
    protected Boolean isVisible(AnActionEvent event) {
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
        Caret caret = event.getData(LangDataKeys.CARET);

        return psiFile != null &&
                PsiJavaFile.class.isAssignableFrom(psiFile.getClass()) &&
                caret != null;
    }
}
