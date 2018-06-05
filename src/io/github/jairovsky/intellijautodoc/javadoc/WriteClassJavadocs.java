package io.github.jairovsky.intellijautodoc.javadoc;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.javadoc.PsiDocComment;
import io.github.jairovsky.intellijautodoc.action.SimpleAction;
import io.github.jairovsky.intellijautodoc.project.ProjectVersionDetector;
import io.github.jairovsky.intellijautodoc.project.ProjectVersionDetectorFactory;
import io.github.jairovsky.intellijautodoc.project.roots.ModuleRootManager;
import io.github.jairovsky.intellijautodoc.text.sentences.SentenceAssembler;
import io.github.jairovsky.intellijautodoc.text.sentences.SentenceAssemblerFactory;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class WriteClassJavadocs implements SimpleAction {

    private final Project project;
    private final List<PsiClass> classesToWrite;
    private final PsiElementFactory elementFactory;
    private final CodeStyleManager codeStyleManager;

    public WriteClassJavadocs(Project project, List<PsiClass> classesToWrite) {
        this.project = project;
        this.classesToWrite = classesToWrite;
        this.elementFactory = PsiElementFactory.SERVICE.getInstance(project);
        this.codeStyleManager = CodeStyleManager.getInstance(project);
    }

    @Override
    public void execute() {

        classesToWrite.forEach(this::createDocumentationForClass);
    }

    private void createDocumentationForClass(PsiClass clazz) {

        String sentence =
            createMainSentenceForJavadoc(clazz);

        String sentencePlusJavadocVersionTag =
            addProjectVersionTagToSentence(sentence);

        insertJavadocIntoTheClassStructure(clazz, sentencePlusJavadocVersionTag);

        codeStyleManager.reformat(clazz);
    }

    private String createMainSentenceForJavadoc(PsiClass clazz) {

        List<String> words =
            newArrayList(clazz.getName());

        SentenceAssembler sentenceAssembler =
            SentenceAssemblerFactory.newAssembler(clazz);

        return sentenceAssembler.assembleSentence(words);
    }

    private String addProjectVersionTagToSentence(String sentence) {

        ProjectVersionDetector projectVersionDetector =
                ProjectVersionDetectorFactory.newDetector(project);

        VirtualFile contentRoot =
            ModuleRootManager.findCurrentModuleRoot(project);

        String projectVersion =
                projectVersionDetector.getProjectVersion(contentRoot);

        if (projectVersion != null) {
            sentence += "\n @version " + projectVersion;
            sentence += "\n @since " + projectVersion;
        }

        return sentence;
    }

    private void insertJavadocIntoTheClassStructure(PsiClass clazz, String sentence) {

        String javadocMarkup =
            wrapInJavadocMarkup(sentence);

        PsiDocComment docComment =
            elementFactory.createDocCommentFromText(javadocMarkup);

        insertComment(clazz, docComment);
    }

    private String wrapInJavadocMarkup(String str) {
        return String.format("/**%s*/", str);
    }

    private void insertComment(PsiClass clazz, PsiDocComment docComment) {

        ASTNode positionBeforeClassDeclaration = clazz.getFirstChild().getNode();
        ASTNode comment = docComment.getNode();

        clazz.getNode().addChild(comment, positionBeforeClassDeclaration);
    }
}
