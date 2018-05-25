import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.annotations.NotNull;

public class GenerateJavadocAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {

        PsiJavaFile f = (PsiJavaFile) event.getData(LangDataKeys.PSI_FILE);

        PsiElementFactory factory = PsiElementFactory.SERVICE.getInstance(f.getProject());

        f.accept(new JavaRecursiveElementWalkingVisitor() {



            @Override
            public void visitMethod(PsiMethod method) {
                super.visitMethod(method);
                System.out.println(method.getName());
                PsiDocComment comment = method.getDocComment();
                if (comment != null) {
                    System.out.println(comment.getText());
                } else {
                    new WriteCommandAction(f.getProject()) {
                        @Override
                        protected void run(@NotNull Result result) {
                            PsiDocComment docCommentFromText = factory.createDocCommentFromText("/**AUTO GENERATED!!!!!!!!!11!!ONZE!!!!*/");
                            method.addBefore(docCommentFromText, method.getFirstChild());
                            CodeStyleManager.getInstance(f.getProject()).reformat(method);
                        }
                    }.execute();
                }
            }



        });

    }
}
