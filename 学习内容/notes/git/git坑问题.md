# git 遇到的问题集合

## git status 中文乱码

在mac系统中git status 中文乱码解决方案是使用  git config --global core.quotepath false 进行设置中文乱码问题就会被解决,结果如图所示。
![2019-12-19-11-58-55](http://jikelearn.cn/2019-12-19-11-58-55.png)

## 配置一些git全局快捷键

```linux
修改 ~/.gitconfig 文件

[alias]
     tree = !git log --graph --decorate --pretty=oneline --abbrev-commit --all
     lg = log --color --graph --branches --pretty=format:'%C(auto)%h %C(auto)%d %C(auto)%s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --decorate --abbrev-commit --all
     cp = cherry-pick
```

## 解决github gist无法访问的问题

mac 终端下操作，ping两个ip

```linux
ping 192.30.253.118
ping 192.30.253.119
能ping通的话，添加到/etc/hosts文件中
192.30.253.118 gist.github.com
192.30.253.119 gist.github.com
保存之后就能ping成功
```

## pre-commit 配置

```yml
# 配置python方面的 ，同样的Java方面也是可以做的
# See https://pre-commit.com/ for usage and config
default_language_version:
  python: python3.7
default_stages: [commit]
stages: [commit,push]
repos:
  - repo: local
    hooks:
      - id: isort
        name: isort
        entry: isort
        language: python
        types: [python]
        exclude: compiled.*

      - id: black
        name: black
        entry: black
        language: python
        types: [python]
        exclude: compiled.*
      - id: flake8
        name: flake8
        entry: pipenv run flake8
        language: python
        types: [python]
        exclude: setup.py

#      - id: mypy
#        name: mypy
#        entry: mypy
#        language: python
#        types: [python]
#        exclude: compiled.* |registration.*



#      - id: pytest
#        name: pytest
#        entry: pipenv run pytest test
#        language: python
#        types: [python]
#
#      - id: pytest-cov
#        name: pytest
#        stages: [push]
#        entry: pipenv run pytest tests/ --cov --cov-fail-under=70
#        language: python
#        types: [python]
#        pass_filenames: false
```

配置文件

```python
[isort]
multi_line_output = 3
include_trailing_comma = True
force_grid_wrap = 0
use_parentheses = True
line_length = 88
skip=./src/dlserver/ct1mm/v1/compiled,./src/dlserver/ct1mm/v3/compiled,


[flake8]
ignore = E203, E266, E501, W503,C901,F601
max-line-length = 88
max-complexity = 18
select = B,C,E,F,W,T4
exclude= ./src/dlserver/ct1mm/v1/compiled,./src/dlserver/ct1mm/v3/compiled

;[mypy]
;files=./src/dlserver
;ignore_missing_imports=true
;show_error_codes=true

;
;[tool:pytest]
;testpaths=tests/

# 放到 pyproject.toml文件中
[tool.black]
exclude="src/dlserver/ct1mm/v1/compiled|src/dlserver/ct1mm/v3/compiled|"

```