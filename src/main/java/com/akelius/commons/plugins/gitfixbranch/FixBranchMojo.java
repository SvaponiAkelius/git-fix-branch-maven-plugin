package com.akelius.commons.plugins.gitfixbranch;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

@Mojo(name = "fix-branch", defaultPhase = LifecyclePhase.VALIDATE)
public class FixBranchMojo extends AbstractMojo {

    @Parameter(property = "branchEnvironmentVariableName", defaultValue = "BRANCH_NAME")
    private String branchEnvironmentVariableName;

    @Parameter(property = "gitPropertiesFilename", defaultValue = "git.properties")
    private String gitPropertiesFilename;

    @Parameter(property = "gitBranchPropertyName", defaultValue = "git.branch")
    private String gitBranchPropertyName;

    @Parameter(property = "project", readonly = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final String branchName = System.getenv(branchEnvironmentVariableName);
        if (branchName != null) {
            final File gitPropertiesFile = findInClasspath(gitPropertiesFilename);
            if (gitPropertiesFile == null) {
                getLog().warn("File git.properties not found in classpath. Abort.");
            } else {
                try (final FileInputStream fis = new FileInputStream(gitPropertiesFile); final FileOutputStream fos = new FileOutputStream(gitPropertiesFile)) {
                    final Properties gitProperties = new Properties();
                    gitProperties.load(fis);
                    getLog().info("Found " + branchEnvironmentVariableName + "=" + branchName + "");
                    getLog().info("Adding " + gitBranchPropertyName + " to git.properties");
                    gitProperties.setProperty(gitBranchPropertyName, branchName);
                    gitProperties.store(fos, "Edited by git-maven-plugin");
                } catch (final IOException e) {
                    getLog().error(e);
                    throw new UncheckedIOException(e);
                }
            }
        }
    }

    private File findInClasspath(final String classpath) {
        final File file = new File(project.getBuild().getOutputDirectory(), classpath);
        if (file.exists()) {
            return file;
        }
        return null;
    }
}
