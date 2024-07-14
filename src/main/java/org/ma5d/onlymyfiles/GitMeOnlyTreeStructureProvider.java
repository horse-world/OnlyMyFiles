package org.ma5d.onlymyfiles;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.util.*;

public class GitMeOnlyTreeStructureProvider implements TreeStructureProvider {

    @SuppressWarnings("NullableProblems")
    @Override
    public Collection<AbstractTreeNode<?>> modify(AbstractTreeNode<?> root, Collection<AbstractTreeNode<?>> children, ViewSettings settings) {
        // 过滤无关紧要数据
        if (!(root instanceof PsiDirectoryNode)) return children;

        // 获取用户名
        String userName = getCommandResult("git config --get user.name").get(0);
        ArrayList<AbstractTreeNode<?>> childrenClone = new ArrayList<>(children);
        for (AbstractTreeNode<?> child : children) {
            // 去除root目录下该用户修改过的文件或目录
            @SuppressWarnings("DataFlowIssue") String directory = ((BasePsiNode<?>) root).getVirtualFile().getPath();
            @SuppressWarnings("DataFlowIssue") String path = ((BasePsiNode<?>) child).getVirtualFile().getPath();
            List<String> commandResult = getCommandResult(String.format("cmd /c cd %s && git log --author=%s %s", directory, userName, path));
            if (commandResult.isEmpty()) childrenClone.remove(child);
        }
        return childrenClone;
    }

    public List<String> getCommandResult(String command) {
        BufferedReader reader = new BufferedReader(new CharArrayReader("OnlyMyFiles".toCharArray()));
        @SuppressWarnings("UnusedAssignment") List<String> res = List.of("ma");
        try {
            reader = Runtime.getRuntime().exec(command).inputReader();
            res = reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("执行命令报错");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("输入流未关闭");
            }
        }
        return res;
    }

}
