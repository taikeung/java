###利用rsync工具进行同步Windows和Linux机器之间的文件

####背景：
>做一个全自动版本发布系统
服务端为windows，客户端为linux。

现有的版本发布步骤为，执行备份脚本进行原有内容备份，通过FTP工具进行版本部署，最后进行脚本对nginx合tomcat服务进行重启。

现在使用rsync工具后，将这三个步骤整合为一个，即以后的版本发布，只要执行一个脚本即可。

####准备内容：
>已windows机器192.1681.15为服务端，准备rsync的windows服务端的版本---cwrsync。

cwrsync分为服务端工具和客户端工具，都是使用在windows下的.

注：cwrsync最新版已经变成收费软件了 官方能下到的免费版本是cwrsync的4.05版本

客户端版本：cwRsync 4.0.5 Installer

服务端版本：cwRsyncServer 4.0.5 Installer

因为此次要做的是将windows机器作为服务端，所以就选择cwRsyncServer 4.0.5 Installer这个即可。

>下载地址:免费下载地址在 http://linux.linuxidc.com/
用户名与密码都是www.linuxidc.com
具体下载目录在 /2013年资料/6月/16日/利用rsync工具进行同步Windows和Linux机器之间的文件

已linux机器192.168.1.216为客户端，准备rsync的linux版本。

正常的话linux机器都会自带rsync。

ps:我的linux机器为CentOS 6.2.

![](http://www.linuxidc.com/upload/2013_06/130616150723641.png)


查询到linux机器确实有自带的rsync工具的。

这样的话软件方面已准备完成。

安装步骤：

此安装步骤针对与windows服务端的。

解压后运行cwRsyncServer_4.0.5_Installer.exe

一路next 默认安装路径为  C:\Program Files\ICW\

不要修改路径，之前估计是修改了路径，导致安装不成功，默认安装就行，安装过程中有一步比较重要的地方

会要求输入用户名 两次密码， 这里不是创建密码 而是输入rsync登陆windows系统的账号密码

![](http://www.linuxidc.com/upload/2013_06/130616150723642.png)

在这里输入你要设置的用户名和密码，这个在两台机器使用rsync相互通信时会使用到的用户名和密码的。

创建过程中若出现安全软件拦截时，要让安全软件允许这样操作，这样才能正常创建成功。


