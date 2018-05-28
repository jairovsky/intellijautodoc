package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

public class InsertJavadocAction extends HideableAction {

    @Override
    public void actionPerformed(AnActionEvent event) {

        PsiJavaFile file = (PsiJavaFile) event.getData(LangDataKeys.PSI_FILE);

        if (file != null) {
            new NoJavadocMethodVisitor(file).execute();
        }
    }

    @Override
    Boolean isVisible(AnActionEvent event) {
        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);

        return PsiJavaFile.class.isAssignableFrom(psiFile.getClass());
    }
}
