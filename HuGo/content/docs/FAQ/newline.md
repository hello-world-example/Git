
# Git 中的换行符问题


在各操作系统下，文本文件所使用的换行符是不一样的

- UNIX/Linux 使用的是 0x0A（`LF`）`\n`，
- 早期的 Mac OS 使用的是0x0D（`CR`）`\r`，后来的 OS X 在更换内核后与 UNIX 保持一致了`\n`
- 但 DOS/Windows 一直使用 0x0D0A（`CRLF` `\r\n`）作为换行符

Git 由大名鼎鼎的 Linus 开发，最初只可运行于 *nix 系统，因此推荐只将 UNIX 风格的换行符保存入库。
但它也考虑到跨平台协作的场景，并且**提供了一个“换行符自动转换”功能**。

这个功能默认处于“自动模式”，**当你在签出(checkout)文件时，它试图将 UNIX 换行符（LF）替换为 Windows 的换行符（CRLF）**； 
当**你在提交(commit)文件时，它又试图将 CRLF 替换为 LF**。


> 如果你手头的这个文件是一个包含中文字符的 UTF-8 文件，那么这个“换行符自动转换”功能 在提交(commit)时是不工作的，但签出时的转换处理没有问题。





## 自动转化（`core.autocrlf`）配置

```
#提交时转换为LF，检出时转换为CRLF
git config --global core.autocrlf true   

#提交时转换为LF，检出时不转换
git config --global core.autocrlf input   

#提交检出均不转换
git config --global core.autocrlf false
```




## 开启提交检查（`core.safecrlf`）

```
#拒绝提交包含混合换行符的文件
git config --global core.safecrlf true   

#允许提交包含混合换行符的文件
git config --global core.safecrlf false   

#提交包含混合换行符的文件时给出警告
git config --global core.safecrlf warn
```




## TortoiseGit 设置

`TortoiseGit` → `Settings` → `Git`

- `Auto CrLf convert` 下
    - 去掉 `AutoCrlf`
    - `SafeCrlf` 选为 `true`
- 勾中 `Save as Global`





## 修改 Git 配置文件 `.gitconfig`
以上通过 git 命令，或者是图形界面，实际上最终改的都是配置文件

```
[core]
    autocrlf = false
    safecrlf = true

```




## IDE 换行符设置为 Unix 格式(`\n`)

## IDEA 

`Editor` > `Code Style` > `Line spearator（for now files）` > `Unix and MacOS(\n)`

## Eclipse

`General` > `Workspace` > `New text file line delimiter` > `Unix`





## Read More

- [GitHub 第一坑：换行符自动转换](http://blog.jobbole.com/46200/)
- [Git中的AutoCRLF与SafeCRLF换行符问题](https://www.cnblogs.com/flying_bat/archive/2013/09/16/3324769.html)
