## 作业网址
[](https://star.jmhui.com.cn/p1/zy.html)
https://star.jmhui.com.cn/p1/zy.html

## 创建gradle项目
`gradle init` 
application  java 默认 默认 yes 4 默认 默认

### 编码
1. File -> Settings -> File Encodings下设置编码格式为UTF-8，此种最常见，一般刚装idea或者刚导入项目的时候会遇到此处编码格式未修改的情况。
2. 在build.gradle 中添加编码设置，gradle默认为系统编码（GBK）
```java
tasks.withType(JavaCompile){
    options.encoding("utf-8")
}
```

### 配置依赖
`build.gradle`
替换repositories 为
```
repositories {
 mavenLocal()
 maven{url 'https://maven.aliyun.com/repository/public'}
 // Use Maven Central for resolving dependencies.
 mavenCentral()
}
```

详细配置--》
[](https://star.jmhui.com.cn/p1/585.html)


## 导入idea大量文件爆红处理
File --> Settings --> Version Control  --> Directory Mappings
vcs 下面的git修改为none 后点击Apply 然后OK



## 生成checkstyle报告
终端输入gradle clean check(需要先配置好checkstyle插件)