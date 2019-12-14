# from time import sleep
import asyncio

# """
# 协程学习
# """

# # async def out_value(value,content):
# #     print(f"value is {value}")
# #     await asyncio.sleep(2)
# #     print(f"content is {content}")

# # asyncio.run(out_value(1,"i am asyncio"))

# async def out_value(time):
#     print(f"time is {time}")

# async def out_second(value):
#     print(f"开始输出, value is {value} ")
#     await out_value(value) 
#     print(f"输出结束,value is {value}")

# asyncio.run(out_second(1))

# async def asyncio_task():
#     task1 = asyncio.create_task(out_value(1))
#     await task1

# asyncio.run(asyncio_task())

async def out_content(content):
    print(f"content is {content}")

loop =  asyncio.get_running_loop()  
loop.run_until_complete(out_content("I am loop"))
loop.close()
