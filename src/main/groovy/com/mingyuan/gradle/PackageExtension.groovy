package com.mingyuan.gradle;
import org.gradle.api.Project;

/**
 * Created by yanxq on 17/1/10.
 */
public class PackageExtension {

    public String jarBaseName = "android_lib";
    public String jarVersion = "";
    public String dstDirName = "artifacts";
    public boolean delOriginJarEnable = true;
    public Properties metaProperties = null;
    public String[] mergeJarFiles = null;

    public String getPackageJarName() {
        return jarBaseName + "-" + jarVersion + ".jar";
    }

    public String getArchiveFileDir() {
        return dstDirName + "/" + jarVersion + "/";
    }

    public File getArchiveFile(Project target) {
        String fileName = getPackageJarName();
        String dirPath = getArchiveFileDir();
        File relativeFile = new File(dirPath,fileName);
        return new File(target.getProjectDir(),relativeFile.getPath());
    }



}
