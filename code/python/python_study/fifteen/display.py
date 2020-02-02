"""
图形化展示
"""
import matplotlib.pyplot as plt


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


if __name__ == '__main__':
    show_pie_image()
