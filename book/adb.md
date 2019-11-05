### apk
```shell
# 安装apk
adb install apkname #安装apk
adb install -r
-l　锁定该应用程序
-r　替换已存在的应用程序，也就是说强制安装
-t　允许测试包
-s　把应用程序安装到sd卡上
-d　允许进行将见状，也就是安装的比手机上带的版本低
-g　为应用程序授予所有运行时的权限

adb uninstall [option] <package>
adb shell pm uninstall -k io.selendroid.testapp #-k参数：保留安装包的数据和缓存目录
```



### push和pull

```shell
adb push 电脑（Window）中的文件夹    Android的文件夹
adb push E:\test /storage/sdcard0/test

adb pull   Android的文件夹 电脑（Window）中的文件夹
adb pull /storage/sdcard0/test E:\test
```



### server

```shell
# 杀掉adb
adb kill-server
adb start-server
```



### 查询设备号

```shell
#查询Build.SERIA 设备硬件序列号SN
adb shell getprop ro.serialno
```





### Wifi连接

```shell
adb通过wifi连上机器
1.先pc和机器连上通过一个wifi
2.打开机器，在设置里看下ip地址
3.机器连上pc, 执行
               adb root
               adb tcpip 555
4.断开usb线，pc端执行
 adb connect ip:5555
 adb disconnect #断开连接
```



### 模拟点击

```shell
Runtime.getRuntime().exec("am force-stop readsense.face25") #在return的地方杀死app
adb shell input tap X Y  #模拟事件处理
Runtime.getRuntime().exec("input tap 955 483 ") #模拟按键位置
```





gpio-test 66 1
gpio-test 1 1



### 日记

```shell
#命令行中，执行命令，清空日志信息
adb logcat -c
#将日志信息输出到指定文件中（该文件不存在，则会新建）
adb logcat > logcat.log
#将日志信息输出到以“日期 时间”命名的文件中
adb logcat > "%date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%时%time:~3,2%分%time:~6,2%.log"

adb logcat --help
adb logcat [选项] [过滤项]

(1) 选项解析

-- "-s"选项 : 设置输出日志的标签, 只显示该标签的日志;
--"-f"选项 : 将日志输出到文件, 默认输出到标准输出流中, -f 参数执行不成功;
--"-r"选项 : 按照每千字节输出日志, 需要 -f 参数, 不过这个命令没有执行成功;
--"-n"选项 : 设置日志输出的最大数目, 需要 -r 参数, 这个执行 感觉 跟 adb logcat 效果一样;
--"-v"选项 : 设置日志的输出格式, 注意只能设置一项;
--"-c"选项 : 清空所有的日志缓存信息;
--"-d"选项 : 将缓存的日志输出到屏幕上, 并且不会阻塞;
--"-t"选项 : 输出最近的几行日志, 输出完退出, 不阻塞;
--"-g"选项 : 查看日志缓冲区信息;
--"-b"选项 : 加载一个日志缓冲区, 默认是 main, 下面详解;
--"-B"选项 : 以二进制形式输出日志;

(2) 过滤项解析

过滤项格式 : <tag>[:priority] , 标签:日志等级, 默认的日志过滤项是 " *:I " ;

-- V : Verbose (明细);
-- D : Debug (调试);
-- I : Info (信息);
-- W : Warn (警告);
-- E : Error (错误);
-- F: Fatal (严重错误);
-- S : Silent(Super all output) (最高的优先级, 可能不会记载东西);


```



### pm

pm工具为包管理（package manager）的简称

可以使用pm工具来执行应用的安装和查询应用宝的信息、系统权限、控制应用
```shell
adb root
adb shell # gain shell access
whoami # check who you are
pm list packages
pm uninstall com.android.chrome
pm install -r Google-Play-services.apk
pm uninstall --user 0 com.google.android.gms
```

```shell
#打印所有的已经安装的应用的包名，如果设置了文件过滤则值显示包含过滤文字的内容
pm list packages [options] [FILTER]
-f	显示每个包的文件位置
-d	使用过滤器，只显示禁用的应用的包名
-e	使用过滤器，只显示可用的应用的包名
-s	使用过滤器，只显示系统应用的包名
-3	使用过滤器，只显示第三方应用的包名
-i	查看应用的安装者

pm list permission-groups #打印所有已知的权限组
pm list permissions [options] [GROUP] #打印权限
pm list permissions –g -d
-g	按组进行列出权限
-f	打印所有信息
-s	简短的摘要
-d	只有危险的权限列表
-u	只有权限的用户将看到列表
用户自定义权限

grant <package_name> <permission>	授予应用权限许可。必需android6.0（API级别23）以上的设备
revoke <package_name> <permission>	撤销应用权限。必需android6.0（API级别23）以上的设备
adb shell pm grant <packageName> android.permission.READ_CONTACTS #授权( 取消权限同理)

pm dump PACKAGE  #打印给定的包的系统状态
adb shell pm dump PACKAGE_NAME
DUMP OF SERVICE package	打印服务信息
DUMP OF SERVICE activity	打印activity信息
DUMP OF SERVICE meminfo	打印当前内存使用信息
DUMP OF SERVICE procstats	打印系统内存使用与一段时间内存汇总
DUMP OF SERVICE usagestats	打印服务器使用状态信息
DUMP OF SERVICE batterystats	打印电池状态信息
```
### dumpsys

 dumpsys是Android自带的debug工具。

```shell
dumpsys -l
service list
activity	ActivityManagerService	AMS相关信息
package	PackageManagerService	PMS相关信息
window	WindowManagerService	WMS相关信息
input	InputManagerService	IMS相关信息
power	PowerManagerService	PMS相关信息	
battery	BatteryService	电池信息
dropbox	DropboxManagerService	<div>调试相关</div>
cpuinfo	CpuBinder	CPU
meminfo	MemBinder	内存
dbinfo	DbBinder	数据库

dumpsys activity [options] [cmd]
-a：dump所有；
-c：dump客户端；
-p [package]：dump指定的包名；
-h：输出帮助信息；

dumpsys activity等价于依次输出下面7条指令：
dumpsys activity intents
dumpsys activity broadcasts
dumpsys activity providers
dumpsys activity services
dumpsys activity recents
dumpsys activity activities
dumpsys activity processes

dumpsys activity |  grep mFocusedActivity #查询栈顶Activity
dumpsys activity p mobi.infolife.ezweather.locker.locker_2 #查询某个App的进程状态
dumpsys activity a mobi.infolife.ezweather.locker.locker_2 #查询某个App所有的Activity状态
dumpsys activity b mobi.infolife.ezweather.locker.locker_2 #查询某个App所有的广播状态
dumpsys activity s mobi.infolife.ezweather.locker.locker_2 #查询某个App所有的Service状态
```



### AM

 am指令是 activity manager的缩写，可以启动Service、Broadcast，杀进程，监控等功能 。

```shell
am [command] [options]
am start [options]	#启动Activity	startActivityAsUser
am startservice		#启动Service	startService
am stopservice		#停止Service	stopService
am broadcast		#发送广播	broadcastIntent
am restart			#重启	restart
am dumpheap <pid> <file>			#进程pid的堆信息输出到file	dumpheap
am send-trim-memory <pid> <level>	#收紧进程的内存	setProcessMemoryTrimLevel
am kill <PACKAGE>	#杀指定后台进程	killBackgroundProcesses
am kill-all			#杀所有后台进程	killAllBackgroundProcesses
am force-stop <PACKAGE>	#强杀进程	forceStopPackage
am hang				#系统卡住	hang
am monitor			#监控	MyActivityController.run

am start [options] <INTENT>
-D:开启debug模式
-W：等待启动完成
--start-profiler<FILE>：将profiler中的结果输出到指定文件中
-P：和--start-profiler一样，区别在于，在app进入idle状态时profiler结束
-R <Count>： 重复启动Activity，但每次重复启动都会关闭掉最上面的Activity
-S:关闭Activity所属的App进程后再启动Activity
--opengl-trace:开启OpenGL tracing
--user <USER_ID> ：使用指定的用户来启动activity，如果不输入，则使用当前用户执行

am start -a android.intent.action.VIEW
-a <ACTION>: 指定Intent action， 实现原理Intent.setAction()；
-n <COMPONENT>: 指定组件名，格式为{包名}/.{主Activity名}，实现原理Intent.setComponent(）；
-d <DATA_URI>: 指定Intent data URI
-t <MIME_TYPE>: 指定Intent MIME Type
-c <CATEGORY> [-c <CATEGORY>] ...]:指定Intent category，实现原理Intent.addCategory()
-p <PACKAGE>: 指定包名，实现原理Intent.setPackage();
-f <FLAGS>: 添加flags，实现原理Intent.setFlags(int )，紧接着的参数必须是int型；
```





