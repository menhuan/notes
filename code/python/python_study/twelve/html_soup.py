import bs4
from bs4 import BeautifulSoup

html ="""
<html>
<head>
  <title>测试</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
  <script type="text/javascript" src="//cdn.diadme.org/mobxsss/2.1/mobx.umd.min.js"></script>
  <script src="//as.cislt.com/g/componen/fastclick.js"></script>
  
<link rel="shortcut icon" href="http://www.baidu.com">
<body>
  <div id="root"></div>
<script type="text/javascript" src="//etsfasds.com/click/w77ainfa.js"></script>
</body>
</html>
"""

soup = BeautifulSoup(html,"lxml")

# 读取html标签
# print(soup.html.name)
# print(soup.html.link.attrs) 
# print(soup.link.attrs)
# print(soup.link)
# soup.link.string = "update"
# print(soup.link)
# print(soup.link['rel'])
# soup.link['rel']= "mod"
# print(soup.link['rel'])

# print(soup.body.contents)
# for children in soup.body.children:
#     print(children)

result = soup.find_all(name = "meta")
print(result)