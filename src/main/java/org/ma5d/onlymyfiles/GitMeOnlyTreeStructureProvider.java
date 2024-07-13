package org.ma5d.onlymyfiles;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class GitMeOnlyTreeStructureProvider implements TreeStructureProvider {

    ArrayList<AbstractTreeNode<?>> nodes = new ArrayList<>();
    String userName = "ma";

    @Override
    public @NotNull Collection<AbstractTreeNode<?>> modify(@NotNull AbstractTreeNode<?> parent, @NotNull Collection<AbstractTreeNode<?>> children, ViewSettings settings) {

        try {
            BufferedReader reader = Runtime.getRuntime().exec("git config --get user.name").inputReader();
            userName = reader.readLine();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (AbstractTreeNode<?> child : children) {
            if (!(child instanceof BasePsiNode)) {
                continue;
            }
            if (child instanceof PsiDirectoryNode) {
                try {
                    String path = Objects.requireNonNull(((PsiDirectoryNode) child).getVirtualFile()).getPath();
                    String name = child.getProject().getName();
                    if (!path.contains(name)) continue;
                    if (path.contains("build")) continue;
                    dfs(nodes, (PsiDirectoryNode) child);
                } catch (NullPointerException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            nodes.add(child);
        }
        return nodes;
    }

    @SuppressWarnings("resource")
    private void dfs(ArrayList<AbstractTreeNode<?>> nodes, PsiDirectoryNode children) throws IOException {
        for (AbstractTreeNode<?> child : children.getChildren()) {
            String path = Objects.requireNonNull(children.getVirtualFile()).getPath();
            if (child instanceof PsiFileNode) {
                List<String> list = new ArrayList<>(Arrays.stream(path.split("/")).toList());
                list.remove(list.size() - 1);
                String directory = String.join("/", list);
                List<String> res = Runtime.getRuntime().exec(String.format("cd %s && git log --author=%s %s", directory, userName, path)).inputReader().lines().toList();
                if (res.isEmpty()) return;
                nodes.add(child);
            }

            if (child instanceof PsiDirectoryNode) {
                List<String> res = Runtime.getRuntime().exec(String.format("cd %s && git log --author=%s %s", path, userName, path)).inputReader().lines().toList();
                if (res.isEmpty()) return;
                nodes.add(child);
                dfs(nodes, (PsiDirectoryNode) child);
            }

        }
    }

}
