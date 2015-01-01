Ansj中文分词的另一选择
=========
项目说明：
===

本项目对Ansj中文分词2.0.8源码进行了以下调整和功能扩充：

1、删除NLP分词；（NLP分词占用内存大，分词效率稍低，一般项目不会使用该功能）

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

下载本项目源码，maven打包，词典使用文件系统，只需拷贝ansj_seg-2.0.8.jar到solr/WEB-INF/lib目录，如果使用MySQL还需拷贝mybatis-3.2.8.jar和mysql-connector-java-5.1.34.jar

solr的schema.xml中添加：

`<fieldType name="text_ansj" class="solr.TextField" positionIncrementGap="100">

    <analyzer type="index">
    
        <tokenizer class="org.ansj.plugin.solr.AnsjTokenizerFactory" isQuery="false" isStemming="true"/>
        
        <filter class="solr.LowerCaseFilterFactory"/>
        
    </analyzer>
    
    <analyzer type="query">
    
        <tokenizer class="org.ansj.plugin.solr.AnsjTokenizerFactory"/>
        
        <filter class="solr.LowerCaseFilterFactory"/>
        
    </analyzer>
    
</fieldType>`

Tokenizer共支持三个参数isQuery，isStemming和stopWords：

isQuery用来判断使用分词的策略是检索时需要的比较精确的分词方式还是建立索引时所需要的比较不精确但是产生词语较多的分词方式，根据选择调用不同的分词器；

isStemming用来判断是否需要处理英文名词的单复数，第三人称，用于处理中文中的部分英文内容；

stopWords用于指定存放停用词的文件；

schema文件详见源码中files文件夹中的schema.xml文件。

下一步计划：
====
1、文件系统词典加载尝试使用nio

2、分词词典支持Redis

3、solr插件支持solr cloud
技术支持
===
Email:94660697@qq.com
