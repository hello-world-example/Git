package xyz.kail.demo.git.jgi;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Kail on 2016/9/14.
 */
public class GitBranchFileContent {

    private final static String REF_REMOTES = "refs/heads/";

    /**
     * 获取指定分支、指定文件的内容
     *
     * @param gitRoot    git仓库目录
     * @param branchName 分支名称
     * @param fileName   文件名称
     */
    public static String getContentWithFile(String gitRoot, final String branchName, String fileName) throws Exception {
        final Git git = Git.open(new File(gitRoot));
        Repository repository = git.getRepository();

        RevWalk walk = new RevWalk(repository);
        Ref ref = repository.findRef(branchName);
        if (ref == null) {
            //获取远程分支
            ref = repository.findRef(REF_REMOTES + branchName);
        }
        //异步pull
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<Boolean> task = new FutureTask<>(() -> {
            /*//创建分支
            CreateBranchCommand createBranchCmd = git.branchCreate();
            createBranchCmd.setStartPoint(REF_REMOTES + branchName).setName(branchName).call();*/
            return git.pull().call().isSuccessful();
        });
        executor.execute(task);

        ObjectId objId = ref.getObjectId();
        RevCommit revCommit = walk.parseCommit(objId);
        RevTree revTree = revCommit.getTree();

        TreeWalk treeWalk = TreeWalk.forPath(repository, fileName, revTree);
        //文件名错误
        if (treeWalk == null)
            return null;

        ObjectId blobId = treeWalk.getObjectId(0);
        ObjectLoader loader = repository.open(blobId);
        byte[] bytes = loader.getBytes();
        return new String(bytes);
    }

    public static void main(String[] args) throws Exception {

        System.out.println(getContentWithFile("D:/git/jgit", "master", "123.txt"));

    }
}
