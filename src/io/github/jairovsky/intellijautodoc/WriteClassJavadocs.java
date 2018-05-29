package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.javadoc.PsiDocComment;
import io.github.jairovsky.intellijautodoc.text.NameSplitter;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class WriteClassJavadocs implements SimpleAction {

    private final List<PsiClass> classesToWrite;
    private final NameSplitter nameSplitter;
    private final PsiElementFactory elementFactory;
    private final CodeStyleManager codeStyleManager;

    public WriteClassJavadocs(Project project, List<PsiClass> classesToWrite) {
        this.classesToWrite = classesToWrite;
        this.nameSplitter = ServiceManager.getService(NameSplitter.class);
        this.elementFactory = PsiElementFactory.SERVICE.getInstance(project);
        this.codeStyleManager = CodeStyleManager.getInstance(project);
    }

    @Override
    public void execute() {

        classesToWrite.forEach(x -> {

            List<String> words =
                    nameSplitter.splitWords(x.getName());

            PsiDocComment docComment =
                    elementFactory.createDocCommentFromText(wrapInJavadocMarkup(joinSentence(words)));

            x.addBefore(docComment, x.getFirstChild());

            codeStyleManager.reformat(x);
        });
    }

    private String joinSentence(List<String> words) {

        return StringUtils.join(words, " ");
    }

    private String wrapInJavadocMarkup(String str) {
        return String.format("/**%s*/", str);
    }
}
