# BigDecimal的使用踩坑记

## BigDecimal怎么判断两个值相等

一般判断两个对象相等的时候是使用equal来判断,但是在BigDecimal中equal判断是使用到了scale保留位数,简单来说对于BigDecimal来说0.10 和0.1是不相等的.

那这时候怎么判断两个值相等呢?就需要使用BigDecimal的comporeto方法了,比较之后==0 就是相等.大于(1)0代表大,小于(-1)0代表小.

参考文档
