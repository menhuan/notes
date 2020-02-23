import pandas
from pyecharts.charts import Bar, Line, Gauge, Pie, Funnel
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


def show_salary_bar(count):
    bar = (
        Bar()
            .add_xaxis(list(count.index))
            .add_yaxis("人数", count.tolist())

    )
    bar.render("month_salary.html")


def show_csv_images():
    """
    display images
    """
    recruiter = pandas.read_csv("recruiter.bak.csv")
    salary = recruiter["month_salary"]
    # 输出数据统计
    print(salary.describe())
    # 将工资按照大到小排序并指明公司昵称
    count = recruiter.groupby(["month_salary"])["month_salary"].count()
    show_salary_bar(count)


def show_pie_image():
    recruiter = pandas.read_csv("recruiter.bak.csv")
    district_count = recruiter.groupby(["district"])["district"].count()
    pie = (
        Pie()
            .add("", [list(z) for z in zip(list(district_count.index), district_count.tolist())],
                 center=["40%", "50%"])
            .set_colors(["blue", "green", "yellow", "red", "wathet", "orange", "purple", "hotpink"])
            .set_global_opts(title_opts=options.TitleOpts(title="Python工作地区分布"), legend_opts=options.LegendOpts(
            type_="scroll", pos_left="80%", orient="vertical"))
            .set_series_opts(label_opts=options.LabelOpts(formatter="{b}: {c}"))
    )
    pie.render("pie.html")


def show_funnel_image():
    recruiter = pandas.read_csv("recruiter.bak.csv")
    work_time = recruiter.groupby(['work_time'])['work_time'].count()
    funnel = (
        Funnel()
            .add("工作年限", [list(z) for z in zip(list(work_time.index), work_time.tolist())],
                 label_opts=options.LabelOpts(position="inside"))
            .set_global_opts(title_opts=options.TitleOpts(title="工作年限漏斗图"))

    )
    funnel.render("funnel.html")

if __name__ == '__main__':
    show_funnel_image()
