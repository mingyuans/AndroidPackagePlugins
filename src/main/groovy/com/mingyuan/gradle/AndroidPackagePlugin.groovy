package com.mingyuan.gradle;

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.Task


/**
 * Created by yanxq on 17/1/10.
 */
class AndroidPackagePlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        preparePackageExtension(target);
        preparePackageTask(target);
    }

    private void preparePackageExtension(Project target) {
        PackageExtension extension = target.extensions.create("packageExtension",PackageExtension.class);

        Properties properties = new Properties();
        properties.load(target.file("gradle.properties").newInputStream())

        extension.jarBaseName = properties.getProperty("PKG_JAR_BASE_NAME")
        extension.jarVersion = properties.getProperty("PKG_VERSION")
        extension.metaProperties = properties.getProperty("PKG_METAS")
        extension.mergeJarFiles = properties.getProperty("PKG_MERGE_JARS")
        extension.delOriginJarEnable = properties.getProperty("PKG_DEL_ORIGIN_JAR")
    }

    private void preparePackageTask(Project target) {
        target.tasks.create("packageProguardAndroidJar",PackageProguardAndroidJarTask.class)
        target.tasks.create("packageAndroidJar",PackageAndroidJarTask.class)
    }

}
