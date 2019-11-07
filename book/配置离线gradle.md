1.下载Gradle。下载地址：https://services.gradle.org/distributions/ 。下载后不要解压。

2.在as项目里面找到gradle/wrapper/gradle-wrapper.properties文件。

修改文件引向

distributionUrl=file:///D:/Android/gradle/gradle-4.4-all.zip

必要时，项目顶层的build.gradle文件修改一下，里面的buildscript---dependencies 下的classpath 'com.android.tools.build:gradle:3.5.0'改对应