# 拷贝指定目录
import shutil

src = "/Volumes/disk/data/gif/iShot2020-03-1214.54.34.png"
dst = "/Volumes/disk/data/gif_bak/"
shutil.copy(src, dst)

# src = "/Volumes/disk/data/gif/"
# dst = "/Volumes/disk/data/gif_bak1/"
# shutil.copytree(src, dst)

# src = "/Volumes/disk/data/feiyan/regi"
# dst = "/Volumes/disk/data/feiyan/regi_move"
# shutil.copytree(src, dst)

#shutil.move(src, dst)
dst = "/Volumes/disk/data/feiyan/regi_move"
shutil.rmtree(dst)