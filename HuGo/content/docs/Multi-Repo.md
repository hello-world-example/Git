# 多远程仓库



```bash
# 增加远程仓库，新的远程仓库是名是 github
$ git remote add github https://github.com/pinpoint-apm/pinpoint.git

# 查看现有的远程分支
$ git remote -v

# ❤❤❤❤ 更新所有的远程分支
$ git fetch --all

# 查看所有远程分支 (-a 所有分支)
$ git branch -r
# 查看所有 tag
$ git tag -l

# ❤❤❤❤ 从远程分支 checkout 出本地分支（origin/v1.7.3 也可以是 tag）
$ git checkout -b dev_v1.7.3 origin/v1.7.3

# ❤ push 指定分支到远程
$ git push origin dev_v1.7.3

# 合并远程分支
$ git merge origin/dev_v1.7.3
```



- 多远程仓库操作的最大不同，在于需要**明确指定远程仓库名**
- 如果要切换到新的本地分支，需要**明确指定从哪个 HASH(start_point) 检出**
- 合并分支的时候可以不用先 `checkout` 到本地，`fetch` 之后可以直接 `git merge 仓库名/分支名`