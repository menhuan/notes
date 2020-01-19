# /html/body/div[3]/div[1]/div/div[1]/div[4]/div[1]
# //*[@id="comments"]

# //*[@id="comments"]/div[1]

import requests
first_url ="https://movie.douban.com/subject/26363254/comments?status=P"
response = requests.get(first_url)
