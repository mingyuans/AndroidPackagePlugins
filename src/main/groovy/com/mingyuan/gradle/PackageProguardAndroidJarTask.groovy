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
    public void proguard() {
        println("on proguard action.")
        //预先配置
        configProguard()
        //开始混淆
        super.proguard()
        //删除源文件
        deleteSourceJarFile(getProject())
    }

    private void configProguard() {
        Project target = getProject();

        configRuleFiles(target)

        PackageExtension extension = target.getExtensions().getByName("packageExtension");
        String archiveDir = extension.getArchiveFileDir();
        archiveDir = new File(project.projectDir,archiveDir).getAbsolutePath();
        println("archive dir : " + archiveDir);

        String targetJarPath = extension.getArchiveFile(target).getAbsolutePath();
        println("jar file path : " + targetJarPath);

        injars(targetJarPath)

        String outJarPath = targetJarPath.replace(".jar","_proguard.jar")
        outjars(outJarPath)

        addLibraryJars(target)

        //输出 mapping 文件
        printmapping(new File(archiveDir,"mapping.txt").getAbsolutePath())

        //不删除无用的文件
        dontshrink()

        //忽略警告,如果不忽略,有警告时会判断编译失败
        ignorewarnings()
    }

    private void addLibraryJars(Project target) {
        def android = target.getExtensions().getByName("android")
        if (android == null) {
            throw new Exception("android extension is null! Don't not apply this plugin before android !")
        }

        List<String> runtimeJarList;
        try {
            runtimeJarList = android.getBootClasspath()
        } catch (Exception e) {

        }

        if (runtimeJarList != null) {
            for (String runtimeJar : runtimeJarList) {
                libraryjars(runtimeJar)
            }
        }


//        if (plugin != null) {
//            List<String> runtimeJarList;
//            MetaClass metaClass = plugin.getMetaClass();
//            if (plugin.getMetaClass().getMetaMethod("getRuntimeJarList")) {
//                runtimeJarList = plugin.getRuntimeJarList()
//            } else if (android.getMetaClass().getMetaMethod("getBootClasspath")) {
//                runtimeJarList = android.getBootClasspath()
//            } else {
//                runtimeJarList = plugin.getBootClasspath()
//            }
//            if (runtimeJarList != null) {
//                for (String runtimeJar : runtimeJarList) {
//                    println("library jar : " + runtimeJar)
//                    //给 proguard 添加 runtime
//                    libraryjars(runtimeJar)
//                }
//            }
//        }
    }

    private void configRuleFiles(Project target) {
        configuration 'proguard-rules.pro'

        File proguardAndroidFile = target.getExtensions().getByName("android").getDefaultProguardFile('proguard-android.txt')
        if (proguardAndroidFile.exists()) {
            configuration proguardAndroidFile
        }

        String aaptRulesFilePath = target.buildDir.absolutePath + "/intermediates/proguard-rules/release/aapt_rules.txt"
        if (new File(aaptRulesFilePath).exists()) {
            configuration aaptRulesFilePath
        }
    }

    private void deleteSourceJarFile(Project target) {
        PackageExtension extension = target.extensions.getByName("packageExtension");
        if (!extension.delOriginJarEnable) {
            return;
        }
        File targetFile = extension.getArchiveFile(target);
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }

}
