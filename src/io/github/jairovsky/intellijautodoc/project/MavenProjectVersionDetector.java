package io.github.jairovsky.intellijautodoc.project;

import com.intellij.openapi.vfs.VirtualFile;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;

class MavenProjectVersionDetector implements ProjectVersionDetector {

    private static final String MAVEN_PROJECT_FILENAME = "pom.xml";
    private static final MavenXpp3Reader MAVEN_READER = new MavenXpp3Reader();

    @Override
    public String getProjectVersion(VirtualFile contentRoot) {

        try {

            return MAVEN_READER
                    .read(contentRoot.findChild(MAVEN_PROJECT_FILENAME).getInputStream())
                    .getVersion();

        } catch (IOException | XmlPullParserException e) {
            throw new CantGetProjectVersion(e);
        }
    }

    @Override
    public Boolean accepts(VirtualFile contentRoot) {

        VirtualFile projectFile = contentRoot.findChild(MAVEN_PROJECT_FILENAME);

        return projectFile != null && projectFile.exists();
    }
}
