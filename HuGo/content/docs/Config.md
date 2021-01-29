# config



## 配置范围

- ` --system` > `/etc/gitconfig`
- ` --global` > `~/.gitconfig`
- ` --local`  > `.git/config`




## 用户信息

```bash
$ git config --global user.name "kail"
$ git config --global user.email ykb553@163.com
```



## 查看配置信息

```bash
$ git config --list
```



查看指定配置信息

```bash
$ git config user.name
kail
```



## 查看所有配置选项

```bash
git help config
```



## 常用配置选项

```bash
# 用户名
user.name <String>

# 用户邮箱
user.email <String>

# 设置默认编辑器
core.editor <String/vim/vi/notepad>

# input 再提交的时候自动转换成 \n, false|true 为不进行转化或者都转化
core.autocrlf <false|input|true>
```



## Read More

- [初次运行 Git 前的配置](https://git-scm.com/book/zh/v1/%E8%B5%B7%E6%AD%A5-%E5%88%9D%E6%AC%A1%E8%BF%90%E8%A1%8C-Git-%E5%89%8D%E7%9A%84%E9%85%8D%E7%BD%AE)
- [自定义 Git - 配置 Git](https://git-scm.com/book/zh/v2/%E8%87%AA%E5%AE%9A%E4%B9%89-Git-%E9%85%8D%E7%BD%AE-Git)