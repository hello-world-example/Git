package xyz.kail.demo.git.jgit;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, GitAPIException {
        String repoPath = "/Users/kail/IdeaProjects/github/hello-world-example/Git";


        try {
            final JGitRepos.Repo repo = JGitRepos.instance(repoPath);

            // git diff 变化的文件
            System.out.println(repo.diffFiles());

            final RevCommit revCommit = repo.commitInfo("6cdc7aec6ff8871a32d1b9eeec4124e483a9cb27");
            System.out.println("revCommit: " + revCommit.toString());
            System.out.println("getAuthorIdent: " + revCommit.getAuthorIdent());
            System.out.println("getCommitterIdent: " + revCommit.getCommitterIdent());
            System.out.println("getFullMessage: " + revCommit.getFullMessage());

            // 两次提交之间的变化的文件
            final List<String> diffFiles = repo.diffFiles(
                    "01b93206002506f2797398f7c08713ac2115eed7",
                    "6cdc7aec6ff8871a32d1b9eeec4124e483a9cb27"
            );
            System.out.println(diffFiles);

            // 获取变动的文件内容
            final Map<String, byte[]> readFiles = repo.readFiles("01b93206002506f2797398f7c08713ac2115eed7", diffFiles);
            final byte[] bytes = readFiles.get("docs/docs/without-toc/index.html");
            System.out.println(new String(bytes, StandardCharsets.UTF_8));


        } finally {
            JGitRepos.close(repoPath);
        }


    }

}
