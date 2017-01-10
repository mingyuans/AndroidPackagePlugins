package com.mingyuan.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import proguard.gradle.ProGuardTask

import java.text.ParseException

/**
 *
 * Created by yanxq on 17/1/10.
 */
class PackageProguardAndroidJarTask extends ProGuardTask {

    @TaskAction
    public void onTaskAction() throws ParseException,IOException {
        configProguard();


    }

    private void configProguard() {
//        Project target = getProject();
//
//        configRuleFiles()
//
//        PackageExtension extension = target.getExtensions().getByName("packageExtension");
//        String archiveDir = BuildAndroidLibraryJar.getLibraryJarDirPath(buildConfig);
//        archiveDir = new File(project.projectDir,archiveDir).absolutePath;
//
//        String targetJarPath = BuildAndroidLibraryJar.getLibraryJarPath(buildConfig);
//        targetJarPath = new File(project.projectDir,targetJarPath).absolutePath;
//
//        injars(targetJarPath)
//
//        String outJarPath = targetJarPath.replace(".jar","_proguard.jar")
//        outjars(outJarPath)
//
//        addLibraryJars()
//
//        //输出 mapping 文件
//        printmapping(new File(archiveDir,"mapping.txt").getAbsolutePath())
//
//        //不删除无用的文件
//        dontshrink()
//
//        //忽略警告,如果不忽略,有警告时会判断编译失败
//        ignorewarnings()
    }

}
