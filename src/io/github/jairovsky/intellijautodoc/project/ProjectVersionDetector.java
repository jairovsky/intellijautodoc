package io.github.jairovsky.intellijautodoc.project;

import com.intellij.openapi.vfs.VirtualFile;

import javax.annotation.Nullable;

public interface ProjectVersionDetector {

    @Nullable
    String getProjectVersion(VirtualFile contentRoot);

    Boolean accepts(VirtualFile contentRoot);
}
