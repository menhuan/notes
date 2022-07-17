from PIL import Image
def makeHead(srcImg):
    tx = Image.open(srcImg).resize((103,103)).convert("RGBA")
    mb = Image.open('src/tx/mb.png')
    mb.paste(tx, mask=mb)
    return mb
def drawHead(bgImg,startTop,who):
    bg=Image.open(bgImg)
    if who=="Me":
        startLeft=946
        srcImg="src/tx/M.jpg"
    else:
        startLeft=26
        srcImg="src/tx/U.jpg"
        rd=makeHead(srcImg).convert("RGBA")
        r,g,b,a = rd.split()
        bg.paste(rd,(startLeft,startTop,startLeft+103,startTop+103),mask=a)
        bg.save("bgEnd.png")

from PIL import Image,ImageDraw,ImageFont

def drawText(txtPic,words,x,y):
    img=Image.open(txtPic)
    draw=ImageDraw.Draw(img)
    fontType = '/System/Library/Fonts/msyh.ttc'#调用系统微软雅黑字体
    font = ImageFont.truetype(fontType,40)#创建字体对象
    i=0
    for w in words:
        if i<16:
            draw.text((x+40*i+i*4,y-8),w, (0,0,0,255), font)
        else:
            draw.text((x+40*(i%16)+(i%16*4),y+i//16*40+22*(i//16)-8),w, (0,0,0,255), font)
        i+=1
    img.save("bgEnd.png")

def createBubble(bdPic,str,tp,who):
    picDir="./images"
    if who=="U":
        picDir="src/U/"

    n=len(str)
    if n<16:
        cenLen=n*40+(n-1)*4
    else:
        cenLen=16*40+(16-1)*4

    lines=n//16+1#最多16个字一行

    bdr=919
    img=Image.open(picDir+"拐角.png").convert("RGBA")
    w,h=img.size
    bg=Image.open(bdPic)
    r,g,b,a =img.split()
    if who=="Me":
        leftStart=919-cenLen-2*w
    else:
        leftStart=1080-919
    bg.paste(img,(leftStart,tp,leftStart+w,tp+h),mask=a)#左上



    top=Image.open(picDir+"上填充.png")#上中
    top=top.resize((cenLen,h))
    bg.paste(top,(leftStart+w,tp,leftStart+w+cenLen,tp+h))


    rt = img.transpose(Image.FLIP_LEFT_RIGHT)#右上
    r,g,b,a =rt.split()
    bg.paste(rt,(leftStart+w+cenLen,tp,leftStart+w+cenLen+w,tp+h),mask=a)


    lr=Image.open(picDir+"左右填充.png")#中间部分
    lr=lr.resize((cenLen+2*w,42*lines+(lines-1)*22))
    bg.paste(lr,(leftStart,tp+h,leftStart+w+cenLen+w,tp+h+42*lines+(lines-1)*22))


    rt = img.transpose(Image.FLIP_TOP_BOTTOM)#左下
    r,g,b,a =rt.split()
    bg.paste(rt,(leftStart,tp+h+40*lines+(lines-1)*22,leftStart+w,tp+h+40*lines+(lines-1)*22+h),mask=a)


    bg.paste(top,(leftStart+w,tp+h+40*lines+(lines-1)*22,leftStart+w+cenLen,tp+h+40*lines+(lines-1)*22+h))#下中

    rt = img.transpose(Image.FLIP_LEFT_RIGHT)#右下
    rt=rt.transpose(Image.FLIP_TOP_BOTTOM)
    r,g,b,a =rt.split()
    bg.paste(rt,(leftStart+w+cenLen,tp+h+40*lines+(lines-1)*22,leftStart+w+cenLen+w,tp+h+40*lines+(lines-1)*22+h),mask=a)



    arr=Image.open(picDir+"箭头.png").convert("RGBA")
    r,g,b,a =arr.split()
    if who=="Me":
        bg.paste(arr,(919,tp+h,919+13,tp+h+26),mask=a)
        bg.save("bgEnd.png")
    else:
        arrL=arr.transpose(Image.FLIP_LEFT_RIGHT)
        r,g,b,a =arrL.split()
        bg.paste(arrL,(leftStart-13,tp+h,leftStart,tp+h+26),mask=a)

    bg.save("bgEnd.png")

    return (leftStart,tp+h+42*lines+(lines-1)*22+h)

def Chat(ImgPath,text,startHeiht,who):
    ls=createBubble(ImgPath,text,startHeiht,who)
    drawHead("bgEnd.png",startHeiht,who)
    drawText("bgEnd.png",text,ls[0]+24,startHeiht+32)
    return ls[1]


ch=Chat("bgSrc.png","马总，在吗，我是小潘",342,"Me")
ch=Chat("bgEnd.png","有点小事麻烦一下你",ch+32,"Me")
ch=Chat("bgEnd.png","有事说一声就行",ch+32,"U")
ch=Chat("bgEnd.png","都是老朋友了，能办的我一定办",ch+32,"U")
ch=Chat("bgEnd.png","我最近缺钱花了，能不能刷点花呗借呗，你给我设置个免还？",ch+32,"Me")
ch=Chat("bgEnd.png","就这事？",ch+32,"U")
ch=Chat("bgEnd.png","已经搞定，下次留言说一下就好，都是小事！",ch+32,"U")
ch=Chat("bgEnd.png","好的，谢谢",ch+32,"Me")
ch=Chat("bgEnd.png","下次直接留言！",ch+32,"Me")