# soobo


## 前言

`soobo` 是一个 Java 写的静态博客生成器。

先以完成功能为主，代码质量后续再完善。配合自己写的主题，效果可见我的博客：https://sooxin.github.io。

**目前功能尚不完善，仍在持续开发中。除交流学习外，暂不建议使用。**

## 说明

该项目目前有两个分支：`master`和`dev`，其中`dev`用于测试和开发。

## 已实现

目前实现的命令：

- 初始化目录为项目根目录：`java -jar sooobo.jar -init dirPath`
- 切换并使用项目：`java -jar soobo.jar -use projectName`
- 编译一篇 markdown文章，渲染为 `html` 文件，并保存到数据库：`java -jar soobo.jar -gen markdownFilePath`
- 生成首页：`java -jar soobo.jar -index`
- 生成归档页：`java -jar soobo.jar -arc`
- 同时编译文章，生成首页和归档页：`java -jar soobo.jar -ga  markdownFilePath`

## 计划特性

下一步计划：

- 计划支持的命令：
  - 删除某一篇博文，删除对应的 `html` 文件和数据库数据：`java -jar soobo -del articleId`
  - 重新编译某一篇文章：`java -jar soobo.jar -regen markdownFilePath`
- 其他：
  - 完善和持续优化前端主题 `BlackLovesWhite` ，目前样式可参见我的博客：https://sooxin.github.io ，争取以独立的 repo 发出来。



## 声明

水平有限，如有不妥之处，欢迎交流学习。

**再次说明：该项目还在开发中，除交流学习外，不建议使用。**

本项目目前采用 [LGPL 3.0](https://github.com/sooxin/soobo-dev/blob/master/LICENSE) 协议。