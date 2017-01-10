package com.mingyuan.gradle;

import com.mingyuan.gradle.exception.PackageException;
import org.gradle.api.Project;
import org.gradle.api.java.archives.Attributes
import org.gradle.api.tasks.ParallelizableTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.bundling.Jar;

/**
 * Created by yanxq on 17/1/10.
 */
@ParallelizableTask
public class PackageAndroidJarTask extends Jar {

    private static final String[] EXCLUDE_FILES = ["**/R.class","**/R/*.class",] as String[]

    @TaskAction
    public void onTaskAction() {
        Project target = getProject();

        prepareManifest();

        PackageExtension extension = (PackageExtension) target.getExtensions()
                .getByName("packageExtension");

        File archiveFile = extension.getArchiveFile(getProject());
        deleteOldArchiveFileIfExist(archiveFile);

        File classesDirFile = new File(target.getProjectDir(),"build/intermediates/classes/release/com/");
        if (!classesDirFile.exists()) {
            throw new PackageException(String.format("%s is not exist!", classesDirFile.getPath()));
        }

        from(classesDirFile.getAbsolutePath());
        for (String excludeFile : EXCLUDE_FILES) {
            exclude(excludeFile);
        }

        setBaseName(extension.jarBaseName);
        setVersion(extension.jarVersion);

        target.println("archive file path :  " + new File(target.getProjectDir(),extension.getArchiveFileDir()).getAbsolutePath())
        into(new File(target.getProjectDir(),extension.getArchiveFileDir()).getAbsolutePath());

        super.copy();


    }


    private void prepareManifest() {
        Attributes attributes = getManifest().getAttributes();
        attributes.put("git-SHA1", TaskUtils.getGitSHA1Value());
        attributes.put("build-date", TaskUtils.getBuildDate());
        // TODO: 17/1/10 待补充
    }

    private void deleteOldArchiveFileIfExist(File archiveFile) {
        File archiveDirFile = archiveFile.getParentFile();
        if (archiveDirFile.exists()) {
            for (File child : archiveDirFile.listFiles()) {
                child.delete();
            }
        }
    }




}
