"""
图形化展示
"""
import matplotlib.pyplot as plt
import numpy
import pandas
from matplotlib import rcParams
from matplotlib.font_manager import FontProperties

rcParams['axes.unicode_minus'] = False
myfont = FontProperties(fname='/System/Library/Fonts/PingFang.ttc',
                        size=15)


def show_bat_image():
    x = [2001, 2004, 2007, 2010, 2013]
    y = [10, 89, 56, 123, 95]
    plt.bar(x, y, width=1)
    plt.show()


def show_line_image():
    x = [0, 1]
    y = [0, 10]
    plt.figure()
    plt.plot(x, y)
    plt.xlabel("time")
    plt.ylabel("length(cm)")
    plt.show()


def show_pie_image():
    plt.figure(figsize=(10, 16))
    size = [10, 25, 20, 45]
    colors = ["red", "green", "blue", "yellow"]
    explode = [0, 0.05, 0, 0.1]
    labels = ["apple", "pears", "grape", "bananas"]
    plt.pie(
        x=size,
        labels=labels,
        colors=colors,
        explode=explode
    )
    plt.axis("equal")
    plt.show()


def show_scatter_image():
    x = numpy.random.randn(100)
    y = numpy.random.randn(100)
    color = ['red', 'blue', '#ff99ff', '#330033']
    plt.scatter(x, y, marker='x', color=color[2])
    plt.xlabel("x轴代表意义", fontproperties=myfont)
    plt.ylabel("y轴代表意义", fontproperties=myfont)
    plt.title("散点图", fontproperties=myfont)
    plt.text(-1, 1, '散点', fontproperties=myfont)
    plt.show()


def show_hist_image():
    x = pandas.Series(numpy.random.randn(200))
    print(x)
    plt.hist(x, bins=20, color="blue")
    plt.xlabel("x轴长度", fontproperties=myfont)
    plt.ylabel("y轴高度", fontproperties=myfont)
    plt.title("直方图", fontproperties=myfont)
    plt.show()


def show_trigonometric_func():
    sin_x = numpy.linspace(0, 200 * numpy.pi, 50)
    sin_y = numpy.sin(sin_x)
    plt.plot(sin_x, sin_y)
    plt.show()
    cos_x = numpy.linspace(0,200*numpy.pi,50)
    cos_y = numpy.cos(cos_x)
    plt.plot(cos_x,cos_y)
    plt.show()


if __name__ == '__main__':
    show_trigonometric_func()
