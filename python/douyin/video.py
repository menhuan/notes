from time import sleep
from liulanqi import get_driver, run
import upload_ksVideo
import upload_xiaohongshu

if __name__ == "__main__":
    try:
        driver = get_driver()
        run(driver)
        upload_ksVideo.run(driver)
#        upload_xiaohongshu.run(driver)
    finally:
        print("执行结束了")
        sleep(10)
        driver.quit()