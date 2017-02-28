#rsync 介绍
rsync是类unix系统下的数据镜像备份工具——remote sync。  
rsync是一个功能非常强大的工具，其命令也有很多功能特色选项，我们下  面就对它的选项一一进行分析说明。
它的特性如下：  
可以镜像保存整个目录树和文件系统。  
可以很容易做到保持原来文件的权限、时间、软硬链接等等。  
无须特殊权限即可安装。
  
>快速：第一次同步时 rsync 会复制全部内容，但在下一次只传输修改过的文件。rsync 在传输数据的过程中可以实行压缩及解压缩操作，
           因此可以使用更少的带宽。
安全：可以使用scp、ssh等方式来传输文件，当然也可以通过直接的socket连接。
支持匿名传输，以方便进行网站镜像。
rysnc 的官方网站：http://rsync.samba.org/，可以从上面得到最新的版本。关于rsync算法的介绍点这里，还有陈皓blog.
rsync的使用
Rsync的命令格式可以为以下六种：
rsync [OPTION]... SRC DEST
rsync [OPTION]... SRC [USER@]HOST:DEST
rsync [OPTION]... [USER@]HOST:SRC DEST
rsync [OPTION]... [USER@]HOST::SRC DEST
rsync [OPTION]... SRC [USER@]HOST::DEST
rsync [OPTION]... rsync://[USER@]HOST[:PORT]/SRC [DEST]

 
rsync有六种不同的工作模式：
>1. 拷贝本地文件；当SRC和DEST路径信息都不包含有单个冒号":"分隔符时就启动这种工作模式。

---

>2.使用一个远程shell程序（如rsh、ssh）来实现将本地机器的内容拷贝到远程机器。当DEST路径地址包含单个冒号":"分隔符时启动该模式。

---

>3.使用一个远程shell程序（如rsh、ssh）来实现将远程机器的内容拷贝到本地机器。当SRC
    地址路径包含单个冒号":"分隔符时启动该模式。

---

>4. 从远程rsync服务器中拷贝文件到本地机。当SRC路径信息包含"::"分隔符时启动该模式。

---

>5. 从本地机器拷贝文件到远程rsync服务器中。当DEST路径信息包含"::"分隔符时启动该模式。

---

>6. 列远程机的文件列表。这类似于rsync传输，不过只要在命令中省略掉本地机信息即可。

---
可以man rsync 参考 rsync 文档，了解详细的使用方法，下面解析一些参数的使用：  
rsync参数的具体解释如下：  
-v, --verbose 详细模式输出  
-q, --quiet 精简输出模式  
-c, --checksum 打开校验开关，强制对文件传输进行校验  
-a, --archive 归档模式，表示以递归方式传输文件，并保持所有文件属性，等于-rlptgoD  
-r, --recursive 对子目录以递归模式处理  
-R, --relative 使用相对路径信息  
-b, --backup 创建备份，也就是对于目的已经存在有同样的文件名时，将老的文件重新命名为~filename。可以使用--suffix选项来指定不同的备份文件前缀。  
--backup-dir 将备份文件(如~filename)存放在在目录下。  
-suffix=SUFFIX 定义备份文件前缀  
-u, --update 仅仅进行更新，也就是跳过所有已经存在于DST，并且文件时间晚于要备份的文件。(不覆盖更新的文件)  
-l, --links 保留软链结    
-L, --copy-links 想对待常规文件一样处理软链结  
--copy-unsafe-links 仅仅拷贝指向SRC路径目录树以外的链结  
--safe-links 忽略指向SRC路径目录树以外的链结  
-H, --hard-links 保留硬链结  
-p, --perms 保持文件权限  
-o, --owner 保持文件属主信息  
-g, --group 保持文件属组信息  
-D, --devices 保持设备文件信息  
-t, --times 保持文件时间信息  
-S, --sparse 对稀疏文件进行特殊处理以节省DST的空间  
-n, --dry-run现实哪些文件将被传输  
-W, --whole-file 拷贝文件，不进行增量检测  
-x, --one-file-system 不要跨越文件系统边界  
-B, --block-size=SIZE 检验算法使用的块尺寸，默认是700字节  
-e, --rsh=COMMAND 指定使用rsh、ssh方式进行数据同步  
--rsync-path=PATH 指定远程服务器上的rsync命令所在路径信息  
-C, --cvs-exclude 使用和CVS一样的方法自动忽略文件，用来排除那些不希望传输的文件  
--existing 仅仅更新那些已经存在于DST的文件，而不备份那些新创建的文件  
--delete 删除那些DST中SRC没有的文件  
--delete-excluded 同样删除接收端那些被该选项指定排除的文件  
--delete-after 传输结束以后再删除  
--ignore-errors 及时出现IO错误也进行删除  
--max-delete=NUM 最多删除NUM个文件  
--partial 保留那些因故没有完全传输的文件，以是加快随后的再次传输  
--force 强制删除目录，即使不为空  
--numeric-ids 不将数字的用户和组ID匹配为用户名和组名  
--timeout=TIME IP超时时间，单位为秒  
-I, --ignore-times 不跳过那些有同样的时间和长度的文件  
--size-only 当决定是否要备份文件时，仅仅察看文件大小而不考虑文件时间  
--modify-window=NUM 决定文件是否时间相同时使用的时间戳窗口，默认为0  
-T --temp-dir=DIR 在DIR中创建临时文件  
--compare-dest=DIR 同样比较DIR中的文件来决定是否需要备份  
-P 等同于 --partial  
--progress 显示备份过程  
-z, --compress 对备份的文件在传输时进行压缩处理  
--exclude=PATTERN 指定排除不需要传输的文件模式  
--include=PATTERN 指定不排除而需要传输的文件模式  
--exclude-from=FILE 排除FILE中指定模式的文件  
--include-from=FILE 不排除FILE指定模式匹配的文件  
--version 打印版本信息  
等等...
下面举例说明rsync的六种不同工作模式:
>
1)拷贝本地文件。当SRC和DES路径信息都不包含有单个冒号":"分隔符时就启动这种工作模式。  
     如：*rsync -a  ./test.c  /backup*  
2)使用一个远程shell程序(如rsh、ssh)来实现将本地机器的内容拷贝到远程机器。当DES路径地址包含单个冒号":"分隔符时启动该模式。  
    如：*rsync -avz  test.c  user@172.16.0.11:/home/user/src*  
3)使用一个远程shell程序(如rsh、ssh)来实现将远程机器的内容拷贝到本地机器。当SRC地址路径包含单个冒号":"分隔符时启动该模式。  
   如：rsync -avz user@172.16.0.11:/home/user/src  ./src  
4)从远程rsync服务器中拷贝文件到本地机。当SRC路径信息包含"::"分隔符时启动该模式。  
    如：*rsync -av user@172.16.0.11::www  /databack*  
5)从本地机器拷贝文件到远程rsync服务器中。当DES路径信息包含"::"分隔符时启动该模式。  
   如：*rsync -av /databack user@172.16.0.11::www*  
6)列远程机的文件列表。这类似于rsync传输，不过只要在命令中省略掉本地机信息即可。  
    如：*rsync -v rsync://172.16.78.192  /www*   
