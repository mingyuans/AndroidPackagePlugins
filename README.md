[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)[ ![Download](https://api.bintray.com/packages/mingyuan/maven/android-package/images/download.svg) ](https://bintray.com/mingyuan/maven/android-package/_latestVersion)  

## Summary
这是一个 Android Gradle Plugins，能够将 Android Library 工程打包成具备指定名称的 jar，并存放于指定目录中 。

## 依赖配置
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.mingyuans.android:package:1.0.05'
    }
}

apply plugin: 'com.mingyuans.package'

```

## 功能使用
### 参数配置
在待打包的工程目录下配置 gradle.properties ，内容如下:

```
## 打包后 jar 的基础名称
PKG_JAR_BASE_NAME=base_jar
## 打包的 jar 版本号
PKG_VERSION=1.0.XX
## ==> 生成后 JAR 文件命名为：base_jar-1.0.XX.jar
## jar 保存的目录(相对于工程目录)
PKG_DST_DIR_NAME=artifacts
## ==> 生成后保存路径为 project.dir/artifacts/1.0.XX/
## 编译混淆 JAR 下，是否删除未混淆的 jar
PKG_DEL_ORIGIN_JAR=true
## 需要补充写到 jar MANIFESET.MF 中的内容
## 格式为 PKG_META_[KEY]=[VALUE]
PKG_META_test_key=test_value
PKG_META_test1_key=test2_value
## ....
## 需要合并到 JAR 中打包的依赖包
PKG_MERGE_JARS=xxxxxx.jar,xxxxx.jar
```
### 执行
```
./gradlew packageAndroidJar

or

./gradlew packageProguardAndroidJar

```

## License  
```
Copyright 2016-2017 Mingyuans

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
