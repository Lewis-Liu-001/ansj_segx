Ansj中文分词的另一种选择
=========
项目说明：
===

本项目对Ansj中文分词2.0.8源码进行了以下调整和功能扩充：

1、删除NLP分词；（理由是NLP分词占用内存大，分词效率稍低，一般项目不会使用该功能）

2、删除与分词无关的功能；

3、将nlp-lang项目中与分词相关的类合并到ansj_seg项目；

4、将用户自定义词库和岐义纠正词库移到classpath目录下，方便与solr的集成；

5、用户自定义词库和岐义纠正词库支持MySQL数据库存储；

6、增加Ansj for solr插件。（solr 4.10.2下测试成功）

使用说明：
====
### 一、词典的配置
文件系统，library.properties文件中设置userLibrarySource=file

MySQL数据库，library.properties文件中设置userLibrarySource=mysql,数据库脚本见源码中files文件夹中MySQL.sql文件，数据库连接配置mybatic-config-ansj.xml

### 二、solr插件的配置
[CentOS 6.5 安装solr 4.10.2](https://github.com/Lewis-Liu-001/ansj_segx/wiki/CentOS-6.5%E4%B8%8ASolr4.10.2%E5%AE%89%E8%A3%85%E8%BF%87%E7%A8%8B)



下一步计划：
====
1、文件系统词典加载尝试使用nio

2、分词词典支持Redis

3、solr插件支持solr cloud
技术支持
===
email:94660697@qq.com
