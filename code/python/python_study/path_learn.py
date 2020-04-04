import os
import shutil

path = "/Volumes/disk/code"
if os.path.isabs(path):
    print(f"{path} 是绝对路径")
else:
    print(f"{path} 不是绝对路径")
if os.path.isdir(path):
    print(f"{path} 是目录")
else:
    print(f"{path} 不是目录")

print(os.path.basename(path))
if os.path.exists(path):
    print(f"{path} 存在")
else:
    print(f"{path} 不存在")

print(os.path.abspath("python_study"))
print(os.path.dirname(path))
print(os.path.getatime(path))
print(os.path.getctime(path))
print(os.path.getmtime(path))
print(os.path.getsize(path))
if os.path.isfile(path):
    print(f"{path} is file")
else:
    print(f"{path} is not file")
result = os.path.join('/dev/media', path)
print(result)
result = os.path.join('/dev/media', "Volumes/disk/code")
print(result)

# 拷贝指定目录
src = "/Volumes/disk/data/gif"
dst = "/Volumes/disk/data/gif_bak/"
shutil.copy(src, dst)
