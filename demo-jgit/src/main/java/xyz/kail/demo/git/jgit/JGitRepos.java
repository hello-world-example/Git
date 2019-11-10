package xyz.kail.demo.git.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class JGitRepos {

    private JGitRepos() {
        // noting
    }

    private static ConcurrentHashMap<String, Repo> gits = new ConcurrentHashMap<>();

    public static synchronized Repo instance(String path) throws IOException {
        if (gits.containsKey(path)) {
            return gits.get(path);
        }

        final Repo jGitRepo = new Repo(Git.open(new File(path)));
        gits.put(path, jGitRepo);
        return jGitRepo;
    }

    public static void close(String path) {
        gits.computeIfPresent(path, (p, jGitRepo) -> {
            jGitRepo.close();
            return jGitRepo;
        });

        gits.remove(path);
    }

    public static final class Repo {

        private Git git;

        private Repo(Git git) {
            this.git = git;
        }

        private void close() {
            git.close();
        }

        /**
         * 变化的文件
         * <p>
         * git diff
         */
        public List<String> diffFiles() throws GitAPIException {
            return git.diff().call().stream().map(DiffEntry::getNewPath).collect(Collectors.toList());
        }

        /**
         * 两次提交之间的变化的文件
         * <p>
         * git diff --name-only $oldrev $newrev
         */
        public List<String> diffFiles(String oldId, String newId) throws IOException, GitAPIException {
            final Repository repository = git.getRepository();

            try (final ObjectReader reader = repository.newObjectReader()) {
                //
                CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
                oldTreeIter.reset(reader, this.commitInfo(oldId).getTree().getId());
                //
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader, this.commitInfo(newId).getTree());

                final List<DiffEntry> diffEntries = git.diff().setOldTree(oldTreeIter).setNewTree(newTreeIter).call();

                //
                return diffEntries.stream().map(DiffEntry::getOldPath).collect(Collectors.toList());
            }
        }

        /**
         * 获取提交的信息
         */
        public RevCommit commitInfo(String id) throws IOException {
            final Repository repository = git.getRepository();
            return new RevWalk(repository).parseCommit(ObjectId.fromString(id));
        }

        /**
         * 获取变动的文件内容
         */
        public Map<String, byte[]> readFiles(String id, List<String> changeFileList) throws IOException {
            Map<String, byte[]> fileData = new HashMap<>();

            final Repository repository = git.getRepository();

            RevTree revTree = this.commitInfo(id).getTree();

            for (String changeFile : changeFileList) {

                try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                    TreeWalk treeWalk = TreeWalk.forPath(repository, changeFile, revTree);

                    repository.open(treeWalk.getObjectId(0)).copyTo(out);

                    out.flush();

                    // 保存文件字节
                    fileData.put(changeFile, out.toByteArray());
                }
            }

            return fileData;
        }
    }

}
