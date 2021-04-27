前一段时间一致在折腾liunx，然后安装了一个solr，虽然安装的是单机版的，但是还是很兴奋的。输入liunx的命令行是让我们着迷的一件事情，今天就把这些写下来，也算自己对这方面的知识总结吧。
####需要的安装的文件内容
1.  jdk -8u-liunx-x64.tar.gz
2. tomcat
3. solr-4.10.3.tgz.gz
4. IK Analyzer 2012FF_hf1
5. zookeeper-3.4.6.tar.gz
 如果只是单机版的solr的话是不需要安装zookeeper的

#jdk安装
 1. 我在home目录下直接解压***jdk  tar -zxvf   jdk -7u-liunx-x64.tar.gz*** ，并且把这个作为安装目录
 2. 配置环境变量
    vim /etc/profile
   添加如下内容：JAVA_HOME根据实际目录来
   JAVA_HOME=/home/jdk1.7.0_55
   CLASSPATH=$JAVA_HOME/lib/
   PATH=$PATH:$JAVA_HOME/bin
   export PATH JAVA_HOME CLASSPATH
 3. 重新启动机器或者执行另外的命令
   sudo shutdown -r now或者 source /etc/profile
 4. 查看安装情况
    java -version 

#tomcat安装
1. 直接解压tomcat文件即可***tar -zxvf   tomcat***文件。

#solr安装
1. 解压solr文件的安装包***tar -zxvf solr-4.10.3.tgz.gz
2. 进入解压后的文件 然后进入disk文件中找到 solr-4.10.3.war文件 复制solr-4.10.3.war包到我们的tomcat中的webapps下面 然后启动tomcat  启动命令为bin/startup.sh
![图示disk内容](http://upload-images.jianshu.io/upload_images/4237685-6a531e2a09777376.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)3. 将/home/solr-4.10.3/example/lib/ext中的jar包都复制到solr工程当中路径为/home/solr/tomcat/webapps/solr/WEB-INF/lib，这里的路径可以自己自定义。复制/home/solr-4.10.3/example/solr文件复制为solrhome  将其放到home/solr/下。最后一步我们去修改一下solr中的web.xml让其文件能找到我们刚才设置的solrhome
```
<Context path="/solr" docBase="/home/solr/tomcat/webapps/solr" 
      debug="0" privileged="true">
  <Environment  name="solr/home" type="java.lang.String"
     value="/home/solr/solrhome" override="true"/>
 ```
  重新启动下tomcat 。访问 如果tomcat能访问到，而solr访问不到，那么就需要重新看下这几步是否都做全了，或者刚开始解析的内容是否都解析了。
#配置中文解析器、自定义业务域
 1. 还是紧接着我们上面的操作。把IKAnalyzer依赖的jar包添加到solr工程中。并且把分析器使用到词典也添加到classess中，如果**/home/solr/tomcat/webapps/solr/WEB-INF/**没有classess文件 那么需要自己先创建classess文件夹   然后把 **solr/WEB-INF/lib**中的ext_stopword.dic IKAnalyzer.cfg.xml  mydict.dic 这三个文件放到我们新创建的文件classess中
 2. 自定义我们需要的FieldType,在Schema.xml中添加
```
<fieldType name="text_ik" class="solr.TextField">
  <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
</fieldType>
```
 3. 自定义域
```
<field name="item_title" type="text_ik" indexed="true" stored="true"/>
<!--这个name可以自定义但是type必须依赖我们上面定义的分析器-->
<!--我们还可以定义哦哦复制域-->
<copyField source="item_title" dest="item_keywords"/>
```
 4. 重新启动tomcat

![重新启动的tomcat](http://upload-images.jianshu.io/upload_images/4237685-88aaa45db7d4c313.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
