import pickle

content ={
    "str": "this is str",
    "map": {
        "key":"value",
        "num":1,
    },
    "list":[1,2,3]
}
value = pickle.dumps(content)
pickle.