from pyecharts.charts import Bar, Line, Gauge
from pyecharts.faker import Faker
from pyecharts import options


def show_bar_image():
    bar = Bar()
    bar.add_xaxis(["Readmi Note7", "小米9", "小米MIX 3", "小米9SE"])
    bar.add_yaxis("小米", [2000, 900, 300, 500])
    bar.render("bar.html")


def shou_line_image():
    line = (
        Line()
            .add_xaxis(Faker.choose())
            .add_yaxis("消费者A", Faker.values())
            .add_yaxis("消费者B", Faker.values())
            .set_colors(["blue", "red"])
            .set_global_opts(title_opts=options.TitleOpts(title="折线图"))
    )
    line.render("line.html")


def show_gauge_image():
    gauge = (
        Gauge()
            .add(
            "KPI完成率",
            [("已完成", 89.3)],
            axisline_opts=options.AxisLineOpts(
                linestyle_opts=options.LineStyleOpts(
                    color=[(0.25, "#548C00"), (0.5, "#FF5809"), (0.75, "#FF0080"), (1, "#FF0000")], width=20
                )
            ),
        )
            .set_global_opts(
            title_opts=options.TitleOpts(title="仪表盘示例"),
            legend_opts=options.LegendOpts(is_show=True)
        )
    )
    gauge.render("gauge.html")


if __name__ == '__main__':
    show_gauge_image()
