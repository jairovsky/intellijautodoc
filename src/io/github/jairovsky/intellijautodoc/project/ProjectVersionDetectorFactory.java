package io.github.jairovsky.intellijautodoc.project;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.jairovsky.intellijautodoc.project.roots.ModuleRootManager;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ProjectVersionDetectorFactory {

    private static final List<ProjectVersionDetector> PROJECT_VERSION_DETECTORS;

    static {
        PROJECT_VERSION_DETECTORS = newArrayList(
                new MavenProjectVersionDetector()
        );
    }

    public static ProjectVersionDetector newDetector(Project project) {

        VirtualFile contentRoot =
                ModuleRootManager.findCurrentModuleRoot(project);

        return PROJECT_VERSION_DETECTORS.stream()
                .filter(x -> x.accepts(contentRoot))
                .findFirst()
                .orElse(new UndefinedVersion());
    }
}
