
# Git忽略已经被提交的文件

 **`.gitignore` 文件只能作用于 `Untracked Files`**，也就是那些从来没有被 Git 记录过的文件，即从未 add 及 commit 过的文件


1. 从 Git 的数据库中删除对于该文件的追踪 （**git rm `--cached`  files**）
2. 把对应的规则写入 `.gitignore`，让忽略真正生效；
3. 提交＋推送。

`git rm --cached` 删除的是追踪状态，而不是物理文件；
如果你真的是彻底不想要了，你也可以直接 **rm＋忽略＋提交**


## Read More
- [git忽略已经被提交的文件](https://segmentfault.com/q/1010000000430426)