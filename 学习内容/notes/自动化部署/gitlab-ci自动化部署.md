# gitlab

gitlab ci runner 拉取与注册

## 将仓库注册到runner中

```linux
docker run  --rm  -t -i -v /home/tx-deepocean/gitlab_ci/config:/etc/gitlab-runner   docker.infervision.com/gitlab/gitlab-runner  register -n --docker-image "docker:stable" --docker-volumes /var/run/docker.sock:/var/run/docker.sock --url https://code.infervision.com/ --registration-token  ymyEw7gS9LrGRjrnhzPj --executor docker  --description "docker spring"
```

## gitlab_ci文件注意内容

在写.gitlab_ci.yml文件时里面需要注意的问题。

1. 在gitlab ci文件中已经存储了很多环境变量供我们选择.[基础知识文档链接](https://code.infervision.com/help/ci/yaml/README), [环境变量连接](https://docs.gitlab.com/ee/ci/variables/predefined_variables.html#variables-reference),如果想知道自己的gitlab支持哪些环境变量可以使用在script中输入export就将本系统支持的环境变量输出。
2. only 支持的分支等内容。
3. tags是针对的runner设置的，如果runner中设置了多个版本信息，可以使用tags设置。
4. 保护分支需要使用predect打开设置。设置自己的环境变量时使用。

