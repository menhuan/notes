#### du 命令
1. 命令功能 
    
    显示每个文件和目录的磁盘使用空间
2. 指令集

     -a或-all  显示目录中个别文件的大小。   

     -b或-bytes  显示目录或文件大小时，以byte为单位。   

     -c或--total  除了显示个别目录或文件的大小外，同时也显示所有目录或文件的总和。 

     -k或--kilobytes  以KB(1024bytes)为单位输出。

     -m或--megabytes  以MB为单位输出。   

     -s或--summarize  仅显示总计，只列出最后加总的值。

     -h或--human-readable  以K，M，G为单位，提高信息的可读性。

     -x或--one-file-xystem  以一开始处理时的文件系统为准，若遇上其它不同的文件系统目录则略过。 

     -L<符号链接>或--dereference<符号链接> 显示选项中所指定符号链接的源文件大小。   

      -S或--separate-dirs   显示个别目录的大小时，并不含其子目录的大小。 

      -X<文件>或--exclude-from=<文件>  在<文件>指定目录或文件。   

      --exclude=<目录或文件>         略过指定的目录或文件。    

      -D或--dereference-args   显示指定符号链接的源文件大小。   

      -H或--si  与-h参数相同，但是K，M，G是以1000为换算单位。   

      -l或--count-links   重复计算硬件链接的文件。
3. 使用介绍和实践
    
    首先du 命令查看的文件时当前目录的使用大小或者自己指定文件/目录 的大小
   
![查看当前目录的所有文件大小](http://upload-images.jianshu.io/upload_images/4237685-786bcc5ef2d5a1d1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 
-sh   代表的是 显示总计的值 并且按照M或者G或者K的单位统计  。* 代表该目录下所有文件，如果想要查看linux 下所有文件的大小 把当前目录移动到根目录下使用该命令即可查看。 一般 只用这个命令即可，其他的看情况用。
#### df命令
1. 命令功能
   df命令用于显示磁盘分区上的可使用的磁盘空间。默认显示单位为KB。可以利用该命令来获取硬盘被占用了多少空间，目前还剩下多少空间等。
 2. 命令参数
    -a或--all：包含全部的文件系统； 
    -block-size=<区块大小>：以指定的区块大小来显示区块数目；
    -h或--human-readable：以可读性较高的方式来显示信息； 
    -H或--si：与-h参数相同，但在计算时是以1000 Bytes为换算单位而非1024 Bytes； 
    -i或--inodes：显示inode的信息； 
    -k或--kilobytes：指定区块大小为1024字节； 
    -l或--local：仅显示本地端的文件系统； 
    -m或--megabytes：指定区块大小为1048576字节； 
    --no-sync：在取得磁盘使用信息前，不要执行sync指令，此为预设值；
    --portability或者-P：使用POSIX的输出格式；
    --sync：在取得磁盘使用信息前，先执行sync指令；
    -t<文件系统类型>或--type=<文件系统类型>：仅显示指定文件系统类型的磁盘信息； 
    -T或--print-type：显示文件系统的类型； 
    -x<文件系统类型>或--exclude-type=<文件系统类型>：不要显示指定文件系统类型的磁盘信息； 
    --help：显示帮助； 
    --version：显示版本信息。
3. 实践
   
![显示文件系统和大小目录等信息](http://upload-images.jianshu.io/upload_images/4237685-fe0969d55281f425.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  
![格式化显示的内容大小](http://upload-images.jianshu.io/upload_images/4237685-7ddcf7b7eea85846.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![查看所有的文件系统](http://upload-images.jianshu.io/upload_images/4237685-2b0f8650c4c70ada.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



    
     
