package com.mingyuan.gradle

import com.mingyuan.gradle.exception.PackageException
import org.gradle.api.Project
import org.gradle.api.java.archives.Attributes
import org.gradle.api.tasks.ParallelizableTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar

import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * Created by yanxq on 17/1/10.
 */
@ParallelizableTask
public class PackageAndroidJarTask extends Jar {

    private static final String[] EXCLUDE_FILES = ["**/R.class","**/R/*.class",] as String[]

    @TaskAction
    public void onTaskAction() {
        Project target = getProject();
        PackageExtension extension = (PackageExtension) target.getExtensions()
                .getByName("packageExtension");


        File archiveFile = extension.getArchiveFile(getProject());
        deleteOldArchiveFileIfExist(archiveFile);

        File classesDirFile = new File(target.getProjectDir(),"build/intermediates/classes/release/");
        if (!classesDirFile.exists()) {
            throw new PackageException(String.format("%s is not exist!", classesDirFile.getPath()));
        }

        from(classesDirFile.getAbsolutePath());
        for (String excludeFile : EXCLUDE_FILES) {
            exclude(excludeFile);
        }

        prepareManifest(extension);
        settingMergeFiles(target,extension);
        setBaseName(extension.jarBaseName);
        setVersion(extension.jarVersion);
        setExtension("jar");
        setDestinationDir(new File(target.getProjectDir(),extension.getArchiveFileDir()))

        startBuild();
    }

    private void settingMergeFiles(Project target ,PackageExtension extension) {
        if (extension.mergeJarFiles == null || extension.mergeJarFiles.length == 0) {
            return;
        }

        HashMap<String,String> dependencies = new HashMap<String,String>();
        for (File dependence : target.configurations.getByName("compile").getFiles()) {
            dependencies.put(dependence.getName(),dependence);
        }

        if (extension.mergeJarFiles != null && extension.mergeJarFiles.length > 0) {
            File projectDependence;
            for (String mergeFileName : extension.mergeJarFiles) {
                if ((projectDependence = dependencies.get(mergeFileName)) != null) {
                    handleManifestProperties(projectDependence)
                    from(target.zipTree(projectDependence.getAbsolutePath()))
                } else {
                    throw new PackageException("Couldn't find merge file : " + mergeFileName);
                }
            }
        }
    }

    private void handleManifestProperties(File dependence) {
        ZipFile zipFile = new ZipFile(dependence);
        ZipEntry metaEntry = zipFile.getEntry("META-INF/MANIFEST.MF");
        InputStream inputStream = zipFile.getInputStream(metaEntry);
        Properties properties = new Properties();
        properties.load(inputStream);

        Attributes attributes = getManifest().getAttributes();
        for (String proper : properties.stringPropertyNames()) {
            if (attributes.get(proper) == null) {
                attributes.put(proper,properties.getProperty(proper));
            }
        }
    }

    private void startBuild() {
        copy();
    }

    private void prepareManifest(PackageExtension extension) {
        Attributes attributes = getManifest().getAttributes();
        attributes.put("git-SHA1", TaskUtils.getGitSHA1Value());
        attributes.put("build-date", TaskUtils.getBuildDate());

        if (extension.metaProperties != null && !extension.metaProperties.isEmpty()) {
            for (Map.Entry<String,String> entry : extension.metaProperties) {
                attributes.put(entry.getKey(),entry.getValue());
            }
        }
    }

    private void deleteOldArchiveFileIfExist(File archiveFile) {
        File archiveDirFile = archiveFile.getParentFile();
        if (archiveDirFile.exists()) {
            for (File child : archiveDirFile.listFiles()) {
                if (child.isFile()) {
                    child.delete();
                }
            }
        } else {
            archiveDirFile.mkdirs();
        }
    }
}
