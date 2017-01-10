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
    public Map<String,String> metaProperties = null;
    public String[] mergeJarFiles = null;


    public void loadPackageConfig(Project target) {
        Properties properties = new Properties();
        properties.load(target.file("gradle.properties").newInputStream())

        jarBaseName = properties.getProperty("PKG_JAR_BASE_NAME")
        jarVersion = properties.getProperty("PKG_VERSION")
        delOriginJarEnable = properties.getProperty("PKG_DEL_ORIGIN_JAR")

        metaProperties = new HashMap<String,String>();
        for (String properName : properties.propertyNames()) {
            if (properName.startsWith("PKG_META_")) {
                String realName = properName.substring(9);
                metaProperties.put(realName,properties.getProperty(properName));
            }
        }

        String mergeJarFileLine = properties.getProperty("PKG_MERGE_JARS");
        if (mergeJarFileLine != null) {
            mergeJarFiles = mergeJarFileLine.split(",");
        }
    }

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
