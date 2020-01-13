# opencv

opencv是一款由英特尔公司发起并参与开发的跨平台的计算机视觉库，虽然由C++语言编写，但也提供了给多个语言使用如Python，Java等。

本次使用opencv来排除预测结果中假阳的数据，类型分为两种，一个是3a项目(肺大泡,条索,钙化积分)，一个是淋巴结。

opencv需要本地环境安装，在我们的项目中是在虚拟环境中安装

```linux
pip/pipenv install opencv-python
pip/pipenv install opencv-contrib-python
```

本文档中需要的业务逻辑如下：
[肺分割3a去假阳处理步骤](https://wiki.infervision.com/pages/viewpage.action?pageId=138117428)
[淋巴结假阳过滤](https://wiki.infervision.com/pages/viewpage.action?pageId=138117524)

## 梳理步骤

根据提供的逻辑，可以了解两个项目有逻辑实现的共同点也有不同的地方。

### 共同点

两个项目的共同点，

1. 根据**一套图**进行图像处理
2. 将一套图像转换成3d图像(通过CTSeries获取，保证不乱序)以及mask(get_lung_mask)，get_lung_mask是由公司其他同事提供的工具，安装方式为`pipenv/pip install --skip-lock  https://repos.infervision.com/repository/pypi-local/packages/lung-segmentor-itk/2.2.0/lung_segmentor_itk-2.2.0-py2.py3-none-any.whl`
3. 根据同事提供的工具转换成mask

### 不同点

由**共同点操作**得到需要的mask，3a项目与淋巴结的业务逻辑就不一样，并且在3a中的逻辑也是不同的。

#### 3a

3a中首先必要的步骤是找出图像的轮廓，并将这些轮廓组成一个。

图像的凸包查找步骤：

1. 将图像二值化：threshold.
2. 寻找轮廓：findContours
3. 组合成一个轮廓，使用numpy的concatenate
4. 找到图像轮廓后使用该轮廓找出凸包：convexHull
5. 在原图像上画出凸包，并将里面的像素点设置为1,就是设置图像颜色时设置为1即可。注意：使用drwaContours时可以根据结果来看像素值是否被改变或者使用修改颜色重点与黑色进行区分。

得到在原图像上画出来的凸包与图像后，再根据文档中的方式进行假阳的过滤。

#### 淋巴结

淋巴结是在**共同点的操作下**得到3dmask图像后，由于过滤规则中是需要将肺以下的部分直接过滤掉，则在过滤坐标计算时，将得到的图像结果序列排除坐标下面的数据。