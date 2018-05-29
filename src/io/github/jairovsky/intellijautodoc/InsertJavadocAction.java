package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import static java.lang.String.format;

public class InsertJavadocAction extends HideableAction {

    private static final Logger logger = Logger.getInstance(InsertJavadocAction.class);

    @Override
    public void actionPerformed(AnActionEvent event) {

        PsiJavaFile file = (PsiJavaFile) event.getData(LangDataKeys.PSI_FILE);
        Integer caretPosition = event.getData(LangDataKeys.CARET).getOffset();

        PsiElement currentFocusedElement = file.findElementAt(caretPosition);

        PsiElement methodOrClass = (currentFocusedElement instanceof PsiMethod) ? (PsiMethod) currentFocusedElement :
                PsiTreeUtil.getParentOfType(currentFocusedElement, PsiMethod.class);

        methodOrClass = (methodOrClass != null) ? methodOrClass :
                PsiTreeUtil.getParentOfType(currentFocusedElement, PsiClass.class);

        UndocumentedMethodVisitor methodVisitor = new UndocumentedMethodVisitor(file);
        UndocumentedClassVisitor classVisitor = new UndocumentedClassVisitor(file);

        new BatchWriteAction(file.getProject(), classVisitor, methodVisitor).execute();
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
