# <center> docker</center>
1. docker命令：	
    - docker images:查看本地存在的docker镜像
    - docker run -idt --name node centos  -p 10080:80 -p 10022:22 /bin/bash:运行docker镜像,centos是镜像的名称，创建docker容器
    - docker ps:查看运行的容器 (默认查看运行的容器) -a 查看所有的容器(包括未运行的容器) -a -q(所有未运行的容器)
    - docker attach f25c1bcb60dd:进入容器id为f25c1bcb60dd的容器
    - exit：进入容器中的linux后，需要退出，但是同时会关闭容器，如果想不关闭容器可以使用快捷键ctrl+p+q.  
    - docker start/stop containerId or containerName:启动容器
    - docker kill  containerId or containerName:杀死容器
    - docker rm containerId or containerName：删除容器
    - docker rmi imageId：删除镜像
    - docker rm \`docker ps -a -q\`:删除所有的未启动的容器
    - docker build -t imageName -f  /home/file/Dockerfile:-f指定dockerfile的位置，也可以省去(docker build -t imageName .)，但是当前路径下必须有一个名为Dockerfile的文件，否则会报错
2. ssh连接docker容器:  
	- 修改root用户密码:passwd
	- 安装openssh:yum install passwd openssl openssh-server -y
    - 启动ssh：/usr/sbin/sshd -D   
    	ssh-keygen -q -t rsa -b 2048 -f /etc/ssh/ssh_host_rsa_key -N ' '   
        ssh-keygen -q -t ecdsa -f /etc/ssh/ssh_host_ecdsa_key -N ' '		
        ssh-keygen -t dsa -f /etc/ssh/ssh_host_ed25519_key  -N ' '
    - docker commit containerId 保存的镜像名称
3. 搭建镜像仓库:
	- 拉取镜像仓库的镜像:docker pull registry
	- 启动镜像：docker run -d -p 5000:5000 -v /opt/data/registry:/tmp/registry registry  
		默认情况下,上传的镜像会放在docker容器的目录下，但是一旦容器删掉镜像仓库也会删掉，所以我们一般情况下会指定本地一个		目录挂载到容器的/tmp/registry下。
    - 修改需要上传镜像的tag，方便后续上传：docker tag busybox 192.168.112.136:5000/busybox
    - 上传镜像：docker push 192.168.112.136:5000/busybox
    - 可能出现的问题:The push refers to a repository [hub.docker.jiankunking.io:5000/haproxy]   
		Get https://hub.docker.jiankunking.io:5000/v1/_ping: http: server gave HTTP response to HTTPS client    
        解决办法：在”/etc/docker/“目录下，创建”daemon.json“文件。在文件中写入：   
        {   
   			 "insecure-registries": [   
        		"hub.docker.jiankunking.io:5000"   
    		]   
		}  
        重启docker:systemctl restart docker.service
    - 查看镜像仓库中的镜像：浏览器地址栏输入http://120.25.79.217:5000/v2/_catalog   

4.  Dockerfile使用(可以解决容器时区问题)
	- FROM:指定基础镜像，FROM omni:mokee(tag)
	- MAINTAINER:指定镜像制作者信息
	- RUN:运行适用于该镜像的命令，如果基础镜像选用的是ubuntu，那么只能使用Ubuntu的命令。
	- CMD:指定你制作出来的镜像在启动成容器时运行命令的默认的参数
	- ENTRYPOINT:设置指令，指定容器启动时执行的命令，可以多次设置，但是只有最后一个有效,该指令的使用分为两种情况，一种是独自使用，另一种和CMD指令配合使用。
当独自使用时，如果你还使用了CMD命令且CMD是一个完整的可执行的命令，那么CMD指令和ENTRYPOINT会互相覆盖只有最后一个CMD或者ENTRYPOINT有效.   
   - UESR:设置container容器的用户，默认是root用户。
   - EXPOSE:设置指令，该指令会将容器中的端口映射成宿主机器中的某个端口。当你需要访问容器的时候，可以不是用容器的IP地址而是使用宿主机器的IP地址和映射后的端口。要完成整个操作需要两个步骤，首先在Dockerfile使用EXPOSE设置需要映射的容器端口，然后在运行容器的时候指定-p选项加上EXPOSE设置的端口，这样EXPOSE设置的端口号会被随机映射成宿主机器中的一个端口号。也可以指定需要映射到宿主机器的那个端口，这时要确保宿主机器上的端口号没有被使用。EXPOSE指令可以一次设置多个端口号，相应的运行容器的时候，可以配套的多次使用-p选项。   
    ```
        \# 映射一个端口  
        EXPOSE port1  
        \# 相应的运行容器使用的命令  
        docker run -p port1 image  

        \# 映射多个端口  
        EXPOSE port1 port2 port3  
        \# 相应的运行容器使用的命令  
        docker run -p port1 -p port2 -p port3 image  
        \# 还可以指定需要映射到宿主机器上的某个端口号  
        docker run -p host_port1:port1 -p host_port2:port2 -p host_port3:port3 image
    ```
  - ENV:用于设置环境变量,ENV JAVA_HOME /path/to/java/dirent
  - ADD or COPY:从src复制文件到container的dest路径,`ADD <src> <dest>`
  - VOLUME:指定挂载点，设置指令，使容器中的一个目录具有持久化存储数据的功能，该目录可以被容器本身使用，也可以共享给其他容器使用。我们知道容器使用的是AUFS，这种文件系统不能持久化数据，当容器关闭后，所有的更改都会丢失。当容器中的应用有持久化数据的需求时可以在Dockerfile中使用该指令。`VOLUME ["/tmp/data"] `
  - WORKDIR：设置指令，可以多次切换(相当于cd命令)，对RUN,CMD,ENTRYPOINT生效。`WORKDIR /path/to/workdir`
  - ONBUILD：在子镜像中执行，ONBUILD 指定的命令在构建镜像时并不执行，而是在它的子镜像中执行  
  - 解决时区和中文显示问题:
         ```
        #时区设置   
        ENV TZ=Asia/Shanghai    
        RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone    

        #中文支持   
        RUN locale-gen zh_CN.UTF-8 &&\  
        DEBIAN_FRONTEND=noninteractive dpkg-reconfigure locales   
        RUN locale-gen zh_CN.UTF-8  
        ENV LANG zh_CN.UTF-8  
        ENV LANGUAGE zh_CN:zh  
        ENV LC_ALL zh_CN.UTF-8   
            
        ```
  
5. docker 4中网络模式:在创建容器时指定容器的网络模式 - -net=bridge(默认)
    -  bridge模式：- -net=bridge，宿主机会创建一个虚拟网卡来分配和管理容器的ip
    -  host模式：- -net=host，容器和宿主机共用宿主机ip和端口
    -  container模式：- -net=container:containerId or containerName，新创建的容器与已存在的容器共享networknamespace
    -  none模式: - - net=none,创建容器不指定ip，端口，需要自定义。