import pandas as pd
from pandas import Series,DataFrame

index_values =Series([1,2,3,4,5])
chart_values =Series([1,2,3,4,5],index={"a","b","c","d","e"})
print(index_values)
print(chart_values)

content = {"a":1,"b":2,"c":"3"}
dict_values = Series(content)
print(dict_values)

df = DataFrame(
{
    "user_id":["00001","00002","00003"],
    "price":[66.5,44,32],
    "age":[17,18,19],
    "city_category":["A","B","C"]
},columns=["user_id","price","city_category","age"]
)
print(df)

df1 = DataFrame(
{
    "price":[66.5,44,32],
    "age":[17,18,19],
    "city_category":["A","B","C"]
},columns=["price","city_category","age"],
index =["0001","0002","0003"])
print(df1)

df2 = DataFrame(
{
    "price":[66.5,44,32],
    "age":[17,18,19],
    "city_category":["A","B","C"]
})
print(df2)