
```

// 取出想要的key值 进行数据统计
db.getCollection('statistics').aggregate([
    {"$project":{"arrayofkeyvalue":{"$objectToArray":"$$ROOT.lesionsNum.name"}}},
    {"$unwind":"$arrayofkeyvalue"},
    {"$group":{"_id":"$arrayofkeyvalue.k",'count':{'$sum':1}}}
   
])

db.getCollection('statistics').aggregate([
    {"$lookup":{
            from: 'label_Version',
            localField: 'txvid',
            foreignField: '_id',
            as: 'version'    
        
        }
    },
    {

      "$match":{ "version.importDate" : {$gte: new Date("2018-07-08"),$lte: new Date("2018-08-20")}}
      }
    ,
    {"$project":{"arrayofkeyvalue":{"$objectToArray":"$$ROOT.lesionsNum.name"},"txvid":"$$ROOT.txvid"}},
    {"$unwind":"$arrayofkeyvalue"},
    {"$group":{"_id":{"key" :"$arrayofkeyvalue.k","txvid":"$txvid"},'count':{'$sum':1}}},
    {"$project":{"txvid":"$_id.txvid" ,"value":[  "$_id.key" ,"$count"],"_id":0  }},
    {"$group":{"_id":"$txvid","content":{"$push":"$value"}}}
    
])


db.getCollection('statistics').aggregate([
      {
     $lookup: {
      from: 'label_Version',
      localField: 'txvid',
      foreignField: '_id',
      as: 'version'
      }
     },{
      $match:{ "version.importDate" : {$gte: new Date("2018-07-08"),$lte: new Date("2018-08-20")}}
    } ,{
     $lookup: {
     from: 'label',
      let: { txtid :"$txtid" ,txvid :"$txvid" , },
      pipeline :[
      { $match:
     {  $expr :
     { $and:
    [  {$eq :["$txtid","$$txtid"] },
     {$eq :["$txvid","$$txvid"] },
    {$eq :["$doctor"  ,"PanFeng"] } //写label里面的查询条件
     ]
     }
      }
      }
      ],
      as : "label"
     }
      },{
      $unwind: {
      path: "$label",
     preserveNullAndEmptyArrays: true
     }
      }
     ,{
      $lookup: {
     from: 'label_Project',
      localField: 'label.txxid',
      foreignField: '_id',
      as: 'project'
      }
      },{
      $match:{
      "project.project":"CT"
     }
      },{
      $project :{"txtid": 1 ,"txvid": 1 ,"directory": 1 }
      <p>
      },{  // 下面是统计 数量
      $group :{
    _id: null,
    count:{$sum :1}
     }
      }
      ]
     )
```

