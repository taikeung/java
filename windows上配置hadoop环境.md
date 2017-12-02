搭建hadoop开发环境：
- 下载hadoop文件并配置HADOOP_HOME并将hadoop的bin配置到path中  
![pic](https://github.com/AndyHooo/java/blob/master/imgs/hadoopfile.png?raw=true)
![env](https://github.com/AndyHooo/java/blob/master/imgs/env.png?raw=ture)
- 添加win的相关依赖到hadoop的bin目录(直接将hadooponwindows-master中的etc和bin直接覆盖hadoop的etc和bin即可) 
![util](https://github.com/AndyHooo/java/blob/master/imgs/winutil.png?raw=true)
- 到hadoop根目录，如果没有data文件夹的话就新建一个，然后在data下分别创建datanode、namenode两个文件夹
![createfile](https://github.com/AndyHooo/java/blob/master/imgs/createfile.png?raw=true)
- 用记事本打开 \hadoop-2.6.5\etc\hadoop\hadoop-env.cmd文件，修改JAVA_HOME为你自己jdk路径。注意：如果你的JDK安装在Program Files目录下，名称用\PROGRA~1\Java 否则中间的空格可能会识别失败。
![jdkenv](https://github.com/AndyHooo/java/blob/master/imgs/jdkenv.png?raw=true)
- 最后，点击命令提示符（管理员）运行命令提示符，切换到hadoop的安装目录。进行以下操作

    1、切换到etc/hadoop目录，运行hadoop-env.cmd
    2、格式化HDFS文件系统，切换到bin目录然后执行命令：hdfs namenode -format
- 启动
	> start-all.cmd
	> 资源管理:http://localhost:8088/
	> 节点管理GUI:http://localhost:50070/；
