import pandas
from pandas import Series, DataFrame

index_values = Series([1, 2, 3, 4, 5])
chart_values = Series([1, 2, 3, 4, 5], index={"a", "b", "c", "d", "e"})
print(index_values)
print(chart_values)

content = {"a": 1, "b": 2, "c": "3"}
dict_values = Series(content)
print(dict_values)

df = DataFrame(
    {
        "user_id": ["00001", "00002", "00003"],
        "price": [66.5, 44, 32],
        "age": [17, 18, 19],
        "city_category": ["A", "B", "C"]
    }, columns=["user_id", "price", "city_category", "age"]
)
print(df)

df1 = DataFrame(
    {
        "price": [66.5, 44, 32],
        "age": [17, 18, 19],
        "city_category": ["A", "B", "C"]
    }, columns=["price", "city_category", "age"],
    index=["0001", "0002", "0003"])
print(df1)

df2 = DataFrame(
    {
        "price": [66.5, 44, 32],
        "age": [17, 18, 19],
        "city_category": ["A", "B", "C"]
    })
print(df2)

df2 = DataFrame(
    {
        "price": [66.5, 44, 32],
        "age": [17, 18, 19],
        "city_category": ["A", "B", "C"]
    })

df2.to_csv("price.csv")

df2.to_json("price.json")
df2.to_excel("price.xls")

json_result = pandas.read_json("price.json")
print(json_result)
for result_key, value in json_result.to_dict().items():
    print(result_key, "    ", value)
print(json_result['age'].to_json())
print(json_result['price'].to_json())
print(json_result['city_category'].to_json())

csv_result = pandas.read_csv("price.csv")
print(csv_result)
print(csv_result['price'])
print(csv_result['age'])

excel = pandas.read_excel("price.xls")
print(excel)

csv_result = pandas.read_csv("price.csv")
print("========csv_result")
print(csv_result)
print(csv_result.head())
print(csv_result.head(2))

print("=========tail result")
print(csv_result)
print(csv_result.tail())
print(csv_result.tail(1))

csv_result = pandas.read_csv("price.csv")
print("=======csv_result")
print(csv_result)
print(csv_result.iloc[2])
print(csv_result.iloc[0:2])

csv_result = pandas.read_csv("price.csv")
print("=======csv_result")
print(csv_result)
print(csv_result.iloc[:, [1]])
print(csv_result.iloc[:, [0, 1]])
csv_result = pandas.read_csv("price.csv")
print("=======csv_result")
print(csv_result)

df = DataFrame(
    {
        "price": [66.5, 44, 32, 66.5],
        "age": [17, 18, 19, 17],
        "city_category": ["A", "B", "C", "A"]
    })

print(df)
df2 = df.drop(columns=["age"])
print(df2)
df3 = df.drop(index=3)
print(df3)

df = DataFrame(
    {
        "price": [66.5, 44, 32, 66.5],
        "age": [17, 18, 19, 17],
        "city_category": ["A", "B", "C", "A"]
    })
print(df)
df4 = df.drop_duplicates()
print(df4)
df5 = df.duplicated()
print(df5)


df = DataFrame(
    {
        "user_id": ["00001", "00002", "00003"],
        "price": [66.5, 44, 32],
        "age": [17, 18, 19],
        "city_category": ["A", "B", "C"]
    }, columns=["user_id", "price", "city_category", "age"]
)

df2 = DataFrame(
    {
        "user_id": ["00004", "00005", "00006"],
        "price": [98, 22, 109],
        "age": [32, 17, 19],
        "city_category": ["A", "B", "C"]
    }, columns=["user_id", "price", "city_category", "age"]
)

df3 = pandas.concat([df,df2])
print("==========df_result")
print(df3)
df4 = pandas.concat([df,df2],ignore_index=True)
print("==========忽略index合并")
print(df4)

city_category = df4.groupby('city_category')
print("=========分组后的结果")
print(city_category)
print(city_category.sum())
print(city_category.mean())