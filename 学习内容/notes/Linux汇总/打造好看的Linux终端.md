# 终端Terminator

1. 安装sudo apt-get install terminator

配置相关 内容 路径是～/.config/teminator/config 如果文件不存在，创建即可。

```Linux
//打造好看的终端
[global_config]
  title_transmit_bg_color = "#d30102"
  focus = system
  suppress_multiple_term_dialog = True
[keybindings]
[profiles]
  [[default]]
    palette = "#2d2d2d:#f2777a:#99cc99:#ffcc66:#6699cc:#cc99cc:#66cccc:#d3d0c8:#747369:#f2777a:#99cc99:#ffcc66:#6699cc:#cc99cc:#66cccc:#f2f0ec"
    background_color = "#2D2D2D" # 背景颜色
    background_image = None
    background_darkness = 0.85
    cursor_color = "#2D2D2D" # 光标颜色
    cursor_blink = True # 光标是否闪烁
    foreground_color = "#EEE9E9" # 文字的颜色
    use_system_font = False # 是否启用系统字体
    font = Ubuntu Mono 13  # 字体设置，后面的数字表示字体大小
    copy_on_selection = True # 选择文本时同时将数据拷贝到剪切板中
    show_titlebar = False # 不显示标题栏，也就是 terminator 中那个默认的红色的标题栏
[layouts]
  [[default]]
    [[[child1]]]
      type = Terminal
      parent = window0
      profile = default
    [[[window0]]]
      type = Window
      parent = ""
[plugins]
```

填写好之后重启 终端就自动生效，不需要再执行其他命令。

## 常用快捷键

|快捷键|卑职|
|----|---|
Ctrl+Shift+T | 新创建一个窗口
|Ctrl+Shift+E    |垂直分割窗口|
|Ctrl+Shift+O  |  水平分割窗口|
 |   F11        | 全屏|
|Ctrl+Shift+C  |  复制|
Ctrl+Shift+V   | 粘贴
Ctrl+Shift+N   | 或者 Ctrl+Tab 在分割的各窗口之间切换
Ctrl+Shift+X   | 将分割的某一个窗口放大至全屏使用
Ctrl+Shift+Z   | 从放大至全屏的某一窗口回到多窗格界面

## 优化配置

使用快捷键即可，需要进行其他配置，点击页面鼠标邮件选择preferences进行图形化设置。
![显示样式](http://jikelearn.cn/2019-06-11-17-35-27.png)

![图形化设置](http://jikelearn.cn/2019-06-11-17-35-42.png)