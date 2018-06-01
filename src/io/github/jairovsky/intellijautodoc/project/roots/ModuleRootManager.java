package io.github.jairovsky.intellijautodoc.project.roots;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;

public class ModuleRootManager {

    public static VirtualFile findCurrentModuleRoot(Project project) {

        Editor selectedTextEditor =
                FileEditorManager.getInstance(project).getSelectedTextEditor();

        VirtualFile file =
                FileDocumentManager.getInstance().getFile(selectedTextEditor.getDocument());

        return ProjectFileIndex.getInstance(project).getContentRootForFile(file);
    }
}
