## 这是什么
一个 Android Gradle Plugins，目前提供便捷的 Android Library 工程打包成 jar 的功能。

## 依赖配置
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.mingyuans.android:package:1.0.04'
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


