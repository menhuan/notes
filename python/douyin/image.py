# -*- coding: utf-8 -*-
"""
Created on Thu Mar 17 11:18:16 2022

@author: Administrator
"""
import math
from PIL import Image,ImageFont,ImageDraw

font_conf = {
   'type':'/usr/share/fonts/truetype/droid/DroidSansFallbackFull.ttf',
   'size':20,
   'rgb':tuple([0,0,0])
}
bg_conf = {
    'rgb':tuple([255,255,255])
}
margin=50

def CreateMutiLinesPic(text,line_size,pic_path=None):
    """
    Create a fixed-width picture with the height depend on the length of the text

    Parameters
    ----------
    text: words to render in picture

    line_size: words length per line

    pic_path: the path of the new picture to save in if it is not None

    Returns
    -------
    None
    """
    line_count=math.ceil(len(text)/line_size)

    font = ImageFont.truetype(font_conf['type'],font_conf['size'])

    # calculate the picture size
    fwidth,fheight = font.getsize('中'*line_size)
    owidth,oheight = font.getoffset('中'*line_size)
    pic_size=[margin*2+fwidth+owidth,margin*2+(fheight+oheight)*line_count]

    # create new picture
    pic = Image.new('RGB', pic_size,bg_conf['rgb'])
    draw = ImageDraw.Draw(pic)
    for i in range(line_count):
        # draw lines
        draw.text((margin,margin+(fheight+oheight)*i), text[i*line_size:(i+1)*line_size], font_conf['rgb'], font)
    if pic_path:
        pic.save(pic_path)
    pic.show()

def CreatePic(text,imgPath=None,size=[1242,1660],margin=margin,backgroundRGB=bg_conf['rgb'],fontType=font_conf['type'],fontRGB=font_conf['rgb']):
    """
    Create a fixed-size picture, and scale the text to fill in one line

    Parameters
    ----------
    text: words to render in picture

    imgPath: the path of the new picture to save in if it is not None

    size: the picture size

    ...

    Returns
    -------
    None  ;save.
    """
    size=tuple(size)
    backgroundRGB=tuple(backgroundRGB)
    fontRGB=tuple(fontRGB)

    image = Image.new('RGB', size, backgroundRGB) # 设置画布大小及背景色
    iwidth, iheight = image.size # 获取画布高宽

    # 计算字节数，RGB双字，英文单字。都转为双字计算
    size=len(text.encode('gbk'))/2
    # 计算字体大小，每两个字号按字节长度翻倍。
    fontSize=math.ceil((iwidth-(margin*2))/size)

    font = ImageFont.truetype(fontType, fontSize) # 设置字体及字号
    draw = ImageDraw.Draw(image)

    fwidth, fheight = draw.textsize(text, font) # 获取文字高宽
    owidth, oheight = font.getoffset(text)

    fontx = (iwidth - fwidth - owidth) / 2
    fonty = (iheight - fheight - oheight) / 2
    draw.text((fontx, fonty), text, fontRGB, font)

    draw.text((fontx, fonty), text, fontRGB, font)
    if imgPath:
        image.save(imgPath) # 保存图片
    image.show()
text="""
我在闺蜜的房间里换衣服，
都脱完了，我才发现屋子里有人，
准确地说，不能是现实中的人，
而是视频里的一张人脸，
空气安静到诡异，我看到了闺蜜手机视频上的备注，
我认出他来了，视频里面那个长相矜贵、气质禁欲的男人，是富婆的哥哥，
你刚刚在看什么，我竭力让自己冷静，
哥哥冷淡的脸停顿了一下，在看股票，
我不信，我的三围比不上你的股票线，
这个男人就是我要攻略的对象，一个只有金钱欲望的性冷淡
"""
CreatePic(text)# others function
CreateMutiLinesPic(text, 20)# my function