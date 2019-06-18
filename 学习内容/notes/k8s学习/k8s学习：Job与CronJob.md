# Job与CronJob

这两种编排方式是操作离线作业编排，比如定时任务执行的程序。

Job对象也利用模板内容，模板如下：

```Yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: pi
spec:
  template:
    spec:
      containers:
      - name: pi
        image: resouer/ubuntu-bc 
        command: ["sh", "-c", "echo 'scale=10000; 4*a(1)' | bc -l "]
      restartPolicy: Never
  backoffLimit: 4
```

kind 指定为job，其他内容如同pod模板一样。

但有一点需要注意的是Job对象创建后，会自动加上一个controller-uid=<随机字符串>Label，是为了保证Job与他管理的Pod之间的匹配关系。

也能避免不同的Job对象所管理Pod发生重合，这种方式不适合用在Deployment等长作业编排的对象上。

离线作业中，restartPolicy = Nerver 代表Pod不应该被重启否则会被重新在计算一遍。如果失败，Job Controller 会尝试创建一个新的Pod出来进行作业，
当然也有最大尝试创建次数，backoffLimit =4 设置，默认是6.

## Batch

批量任务，大部分是需要并行执行，则在Job Controller中，有两个参数来控制。

- spec.parallelism，定义一个Job在任意期间最对启动多少个Pod执行任务。
- spec.completions，定义Job至少完成的Pod数目。

三种方案执行job。
1. 外部管理器+Job模板，使用脚本或者程序替换模板中的一些参数来实现该方式。
2. 设置固定任务数目的并行Job
3. 指定并行度，但不设置固定的completions的值。

## CronJob

定时任务

```Yaml
apiVersion: batch/v1beta1
kind: CronJob
metadata:
  name: hello
spec:
  schedule: "*/1 * * * *"
  # 模板采用该方式
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: hello
            image: busybox
            args:
            - /bin/sh
            - -c
            - date; echo Hello from the Kubernetes cluster
          restartPolicy: OnFailure

```

定时任务可能会产生一个任务还没有执行完毕，另外一个执行任务就开始。
可以设定字段spec.concurrencyPolicy字段设置策略。

1. concurrencyPolicy =Allow. 默认允许同时存在
2. concurrencyPolicy = Forbid，不会创建新的Pod，该周期被跳过。
3. concurrencyPolicy = Replace.新产生的Pod替换旧的，没有执行的Job.

创建失败 被标记为miss，指定时间窗口次数达到100，就会停止创建。
如果时间窗口超过specstrtingDeadlineSeconds 字段指定的值。