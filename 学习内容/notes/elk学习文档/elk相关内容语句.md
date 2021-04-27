
```JSON
{
  "properties": {
    "test_join":{

      "type": "join",
      "relations":{
       "PATIENT_LOCAL_ID":"PATIENT_ID"
      }

    }

  }
}


{
  "properties": {
    "test_join":{

      "type": "join",
      "relations":{
      "PATIENT_LOCAL_ID":"PATIENT_ID",
      "PATIENT_LOCAL_TEST":"PATIENT_TEST"
      }

    }

  }
}



{
  "properties": {
    "test_join":{

      "type": "join",
      "relations":{
      "PATIENT_LOCAL_ID":"PATIENT_ID",
      "PATIENT_LOCAL_TEST":"PATIENT_TEST",
       "PATIENT_TEST_1":"PATIENT_TEST_SON",
       "PATIENT_OLD":["PATIENT"],
       "PATIENT":"PATIENT_SON"
      }

    }

  }
}


// 创建好之后 新增子表信息
http://192.168.111.12:9200/meterdata/autoData/_id?routing=路由值 方便分片处理 ，主文档需要与子文档位于同一个分片中

// 新增父子关系
{
   "PATIENT_ID":"1231241",
   "DESCRIPTION":"TEST CONTENT ",
   "test_join":{
    "name":"PATIENT_ID",
    "parent":"_nfxeGsBB-ntLnTOu4Oa"
    }
}

// 查询 根据父id查找相关数据

{
	"query":{
		"parent_id":{
			"type":"PATIENT_TEST",
			"id":"11"
		}
	}
}
// 根据 父文档查找子文档
http://192.168.111.12:9200/meterdata/_search
{
  "query": {
	"has_parent" : {
            "parent_type" : "PATIENT_LOCAL_TEST",
            "query" : {
                "term" : {
                    "test" : "this"
                }
            }
        }
  }
}

// 根据子文档查找父文档
http://192.168.111.12:9200/meterdata/_search
{
    "query": {
        "has_child" : {
            "type" : "PATIENT_TEST",
            "query" : {
                "term" : {
                    "_id" : "12"
                }
            }
        }
    }
}
```