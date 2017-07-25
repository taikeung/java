# <center>docker容器实现hadoop分布式</center>
1. 准备工作:
	- 安装ssh：
	```
        yum install openssh-server   
        yum install openssh-clients   

        ssh-keygen -q -t rsa -b 2048 -f /etc/ssh/ssh_host_rsa_key -N ' '  
        ssh-keygen -q -t ecdsa -f /etc/ssh/ssh_host_ecdsa_key -N ' '    
        ssh-keygen -t dsa -f /etc/ssh/ssh_host_ed25519_key -N ' '  

		ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa
        cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys
	```
    
	- 安装jdk和配置hadoop
	```
		cd /usr/java
		tar -zxvf jdk-8u91-linux-x64.tar.gz
        
        cd /home
        mkdir hadoop
        cd hadoop
        mkdir tmp
        mkdir namenode
        mkdir datanode
        docker cp hadoop-2.6.5.tar.gz andy:/home/hadoop
        tar -zxvf hadoop-2.6.5.tar.gz
        
        vi /etc/profile
        export JAVA_HOME=/usr/java/jdk1.8.0_91
		export HADOOP_HOME=/home/hadoop/hadoop-2.6.5
		export HADOOP_CONFIG_HOME=$HADOOP_HOME/etc/hadoop
		export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$JAVA_HOME/bin
		export CLASS_PATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
        
        source /etc/profile
        yum install which
	
	```
	- 配置hadoop的xml文件
	```
    	 cd /home/hadoop/hadoop-2.6.5/etc/hadoop
         vi hadoop-env.sh:
         export JAVA_HOME=/usr/Java/jdk1.8.0_91
		
         vi core-site.xml:
         <property>
            <name>hadoop.tmp.dir</name>
            <value>/home/hadoop/tmp</value>
            <description>A base for other temporary directories.</description>
        </property>

        <property>
                <name>fs.default.name</name>
                <value>hdfs://master:9000</value>
                <final>true</final>
                <description>The name of the default file system.  A URI whose
                scheme and authority determine the FileSystem implementation.  The
                uri's scheme determines the config property (fs.SCHEME.impl) naming
                the FileSystem implementation class.  The uri's authority is used to
                determine the host, port, etc. for a filesystem.</description>
        </property>
        
        vi hdfs-site.xml:
        <property>
            <name>dfs.replication</name>
            <value>2</value>
            <final>true</final>
            <description>Default block replication.
            The actual number of replications can be specified when the file is created.
            The default is used if replication is not specified in create time.
            </description>
        </property>

        <property>
            <name>dfs.namenode.name.dir</name>
            <value>/home/hadoop/namenode</value>
            <final>true</final>
        </property>

        <property>
            <name>dfs.datanode.data.dir</name>
            <value>/home/hadoop/datanode</value>
            <final>true</final>
        </property>
        
        cp mapred-site.xml.template mapred-site.xml
        vi mapred-site.xml:
        <property>
            <name>mapred.job.tracker</name>
            <value>master:9001</value>
            <description>The host and port that the MapReduce job tracker runs
            at.  If "local", then jobs are run in-process as a single map
            and reduce task.
            </description>
    	</property>
        
        hadoop namenode -format
        docker commit -m 'hadoop_images' andy hadoop_cent
        
        docker run -idt -h master --name master hadoop_cent
        docker run -idt -h slave1 --name slave1 hadoop_cent
        docker run -idt -h slave2 --name slave2 hadoop_cent
        
        #配置hosts
        ifconfig:查询各节点ip，将各个节点的ip写入各节点的hosts文件：
        vi /etc/hosts
        172.17.0.3  master
        172.17.0.4 slave1
        172.17.0.5 slave2

		#配置slaves：
        在master节点容器中执行如下命令：
        cd $HADOOP_CONFIG_HOME/
        vi slaves:
        slave1
		slave2
        
        在master节点上执行start-all.sh命令
        
	```
    
    - 配置spark集群:
    ```
		tar -zxvf scala-2.11.4.tgz
        tar -zxvf spark-2.2.0-bin-hadoop2.6.tgz
        
        vi /etc/profile:
        
        export JAVA_HOME=/usr/java/jdk1.8.0_91
        export SCALA_HOME=/usr/scala/scala-2.11.4
        export SPARK_HOME=/home/spark/spark-2.2.0-bin-hadoop2.6
        export HADOOP_HOME=/home/hadoop/hadoop-2.6.5
        export HADOOP_CONFIG_HOME=$HADOOP_HOME/etc/hadoop
        export 	PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$JAVA_HOME/bin:$SCALA_HOME/bin:$SPARK_HOME/bin:$SPARK_HOME/sbin
    	export CLASS_PATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
        
        source /etc/profile
        
        cd /home/spark/spark-2.2.0-bin-hadoop2.6/conf
        cp spark-env.sh.template spark-env.sh
		cp slaves.template slaves
        
        vi spark-env.sh:
        export JAVA_HOME=/usr/java/jdk1.8.0_91
        export SCALA_HOME=/home/scala/scala-2.11.4
        export SPARK_MASTER_IP=172.17.0.2
        export SPARK_WORKER_MEMORY=2g
        export HADOOP_CONF_DIR=/home/spark/spark-2.2.0-bin-hadoop2.6/conf
        
        vi slaves:
        172.17.0.2
        172.17.0.3
        172.17.0.4

		cd /home/spark/spark-2.2.0-bin-hadoop2.6/sbin
        ./start-all.sh

	```
    查看集群信息:http://172.17.0.2:8080