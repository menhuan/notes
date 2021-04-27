1. touch 命令
-![image](http://upload-images.jianshu.io/upload_images/4237685-15c796feb91e856f?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
-简单来说touch 命令可以用来建立文件 文件不存在的情况下，也可以更改创建时间等等   
2. pwd 查看目录---一般看目录用pwd 即可但是pwd -P 会不以连结文件显示  注意P必须是大写 显示正常的完成路径 如果文件时连结档文件那么 需要加 -P才能正确显示路径。。。
3. mkdir 命令 创建 目录  
-mkdir + 目录即可  如果是连续创建目录 需要加 -p mkdir -p test1/test2  这样的目录结构。  
-mkdir -m  设置文件的权限。 mkdir -m  属性的代码 +文件名称
-如果 上面两者要一块公用的话 需要 mkdir -p -m 代码号  目录名称  代码号跟umask这个有关
4. cd 切换命令   
-cd ..  返回到当前目录的上一个目录
-cd ~  或者cd ~root 也可 返回自己家的目录 root用户 返回root 
-cd ~fengruiqi   返回自己家的目录  到fengruiqi这个目录下 
-cd /home.fengruiqi/  这个是绝对路径到 目录下
-cd 也表示回到自己家的意思 因为没有自己创建一个新用户 那么没有测试 是否回到 root下还是回到自己的家目录下。
-cd ../fengtest     表示先返回上一个目录然后找到上一个目录中的fengtest文件。那么就能进入刚才那个目录了  。。可以写多个../../这个形式 代表返回多个上个目录
-cd ./fengtest 代表去当前目录下的哪个文件下
-cd - 比如当前目录是 /home/fengruiqi/test  那么cd - 之后就会 返回到/home/fengruiqi  这个目录下 然后 cd - 操作之后就会返回到/home/fengruiqi/test中
5. liunx  前面的[用户名@backup ~]  其中~ 这个符号就代表我们现在在用户组的家目录下面
6. rmdir  -p  +目录名称     删除空目录  下面不能有其他文件 加-p之后下面的空目录
7. ls  文件目录展示  默认会以文档名排序好后展示 不显示.  与 .. 文件
- ![ls命令的使用](http://upload-images.jianshu.io/upload_images/4237685-6964d3fb1ce178ca?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

8. cp命令的使用 
- ![image](http://upload-images.jianshu.io/upload_images/4237685-f0b1a642f5cd0530?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 首先注意一下 千万不要复制目录，因为复制是失败的。只显示略过哪个目录什么的。那么应该怎么复制一个文件夹呢 我们应该使用cp -r 目录名称  被复制的目标地址 如果地址是.  代表的的是当前目录
-cp -i 文件名称  目标目录 如果已存在文件那么会询问是否覆盖
-cp -p 联通文件的属性包含权限 用户 时间 一起复制过去，而非使用默认属性
-cp -r 递归复制 主要用于目录的复制  
-这几个可以连续使用 按照字母的顺序排序
-cp -s 表示 复制的快捷方式
-cp -u 表示更新式复制，是在目标文件与来源文件有差异的时候才会复制。主要用在更新的工作中。
-cp -d  当来源文件作为连接文件的属性，则复制链接文件属性而非文件本身。
-cp -f 为强制复制 ，但是要注意目标文件是否已经存在，存在则复制不成功需要移除后再操作。
9. rm 删除文件
- ![删除命令](http://upload-images.jianshu.io/upload_images/4237685-bc424812c3a95ba6?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
-rm -f  文件名 不能是目录名称  直接删除 不会询问是否要删除这个动作
-rm -i  文件名 删除文件同上不能是目录  会多一个询问的操作
-rm -r  删除目录 ，有询问的操作，进入 删除等询问。
-rm -fr 直接删除目录 ，没有询问的动作
-如果是删除的一个是带有- 开头的文件那么我们需要这样来删除 rm ./-aaa-  在文件名的前面加上./这个操作。这样帮助我们来删除文件因为在linux中一般-后面接的是选项。
10. mv 移动文件与目录或改名。
- ![mv移动命令](http://upload-images.jianshu.io/upload_images/4237685-8992363bf966d014?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
-如果有多个目录文件需要移动到一个目标文件 可以这样写 mv /home/test1  /home/test2   /home/test  最后那个目录一定是目的地目录 就是前面的两个test1与test2 是并入到test中的
11. cat、tail、head等查看文件内容命令
- ![查看文件内容](http://upload-images.jianshu.io/upload_images/4237685-a910411024906ea7?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
-cat ![cat命令](http://upload-images.jianshu.io/upload_images/4237685-fc4b91349ca8e02c?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
-tac 与cat一样的命令 就不多说了 图示上的可以参考练习
-nl 添加行印  ![nl命令](http://upload-images.jianshu.io/upload_images/4237685-9a4b0a27bdc26a73?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)这个自我感觉跟cat -n 差不多但是 比cat -n 要多出来些东西，比如自动在前面补齐0 ,自动加上行号等等。
-more 翻页查询![more操作](http://upload-images.jianshu.io/upload_images/4237685-bec8c41161d4915a?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)  more +文件名称 那么他会显示一部分，然后我们可以看到下面那个白色的More 那里正在等待着我们接下里的操作。![image](http://upload-images.jianshu.io/upload_images/4237685-d9910dbba79e0399?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- less 命令![less操作](http://upload-images.jianshu.io/upload_images/4237685-b1a893936a16af30?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
-head  加文件 默认取出的文件的前10行那么如果我想要多余的或者少点呢？ 可以利用head -n 100（行数） +文件名  n后面的行数最好是正数 不要加负号  如果n是负数，比如一个文件有151行那么就会取前面的51行后面的100行不动。如果超过了那么就是取前100行。ps最后这个没有验证，待验证。
-tail  文件 跟cat命令差不多，也是 tail -n  行数 文件  。但是有点不同的是 如果这个行数加+号后 那么也会出现cat加负号的类似的情况，在这里还有个命令tail -f 文件名 那么他会不断的读取数据，只要有数据写入那么就会在读取。
-cat 与head的共用  。。cat -n /home/test.txt | head -n 100  同理tail 也是一样的。
