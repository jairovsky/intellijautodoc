package io.github.jairovsky.intellijautodoc.project;

import com.intellij.openapi.vfs.VirtualFile;

class UndefinedVersion implements ProjectVersionDetector {

    @Override
    public String getProjectVersion(VirtualFile contentRoot) {
        return null;
    }

    @Override
    public Boolean accepts(VirtualFile contentRoot) {
        return true;
    }
}
