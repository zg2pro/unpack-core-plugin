package org.apache.maven.plugins.dependency.fromConfiguration;

import java.io.File;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

/**
 * This mojo helps copying the group dependencies to the current target
 * @author zg2pro
 */
@Mojo(name = "copy-modules", requiresDependencyResolution = ResolutionScope.TEST, 
        defaultPhase = LifecyclePhase.PROCESS_SOURCES, threadSafe = true)
public class CopyOtherModulesMojo extends CopyMojo {

    @Override
    protected void doExecute()
            throws MojoExecutionException, MojoFailureException {
        MavenProject mp = this.getProject();
        File directoryFile = new File(mp.getBasedir().getAbsolutePath() + "/target/unpackdependencies");
        for (Artifact art : mp.getDependencyArtifacts()) {
            if (StringUtils.equals(art.getGroupId(), mp.getGroupId())) {
                ArtifactItem ai = new ArtifactItem(art);
                ai.setOutputDirectory(directoryFile);
                ai.setDestFileName("unpack-core-plugin.zip");
                copyArtifact(ai);
            }
        }
    }

}
