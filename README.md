# git-fix-branch-maven-plugin

Maven plugin to read branch name from environment variable.

> NOTICE: it needs to run after [git-commit-id-plugin](https://github.com/git-commit-id/git-commit-id-maven-plugin).

```xml
<plugins>
    <plugin>
        <groupId>pl.project13.maven</groupId>
        <artifactId>git-commit-id-plugin</artifactId>
        <version>4.0.0</version>
        <executions>
            <execution>
                <phase>initialize</phase>
                <goals>
                    <goal>revision</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <!--see https://github.com/git-commit-id/git-commit-id-maven-plugin/blob/master/maven/docs/using-the-plugin.md-->
            <generateGitPropertiesFile>true</generateGitPropertiesFile>
        </configuration>
    </plugin>
    <plugin>
        <groupId>com.akelius.commons.plugins</groupId>
        <artifactId>git-fix-branch-maven-plugin</artifactId>
        <version>${project.version}</version>
        <executions>
            <execution>
                <phase>initialize</phase>
                <goals>
                    <goal>fix-branch</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <!--default values-->
            <branchEnvironmentVariableName>BRANCH_NAME</branchEnvironmentVariableName>
            <gitPropertiesFilename>git.properties</gitPropertiesFilename>
            <gitBranchPropertyName>git.branch</gitBranchPropertyName>
        </configuration>
    </plugin>
</plugins>
```
