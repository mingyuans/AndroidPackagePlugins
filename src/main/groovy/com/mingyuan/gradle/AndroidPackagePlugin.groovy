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
        extension.loadPackageConfig(target);
    }

    private void preparePackageTask(Project target) {
        target.tasks.create("packageAndroidJar",
                PackageAndroidJarTask.class)
                .dependsOn("bundleRelease");

        target.tasks.create("packageProguardAndroidJar",
                PackageProguardAndroidJarTask.class)
                .dependsOn("packageAndroidJar");
    }

}
