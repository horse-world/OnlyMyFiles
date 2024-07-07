package org.ma5d.onlymyfiles;

import com.intellij.icons.ExpUiIcons;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.BasePsiNode;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
// import org.jetbrains.plugins.gradle.projectView.GradleTreeStructureProvider;

public class GitMeOnlyTreeStructureProvider implements TreeStructureProvider {

    @Override
    public @NotNull Collection<AbstractTreeNode<?>> modify(@NotNull AbstractTreeNode<?> parent, @NotNull Collection<AbstractTreeNode<?>> children, ViewSettings settings) {
        ArrayList<AbstractTreeNode<?>> nodes = new ArrayList<>();

        for (AbstractTreeNode<?> child : children) {
            if(!(child instanceof BasePsiNode)){
                continue;
            }
            if(child instanceof PsiDirectoryNode){
                dfs(nodes, (PsiDirectoryNode) child);
            }
            nodes.add(child);
        }
        return nodes;
    }

    private void dfs(ArrayList<AbstractTreeNode<?>> nodes, PsiDirectoryNode children) throws IOException {
        for (AbstractTreeNode<?> child : children.getChildren()) {
            BufferedReader reader = Runtime.getRuntime().exec("git config --get user.name").inputReader();
            String userName = reader.readLine();
            reader.close();

            Runtime.getRuntime().exec("git config --get user.email").inputReader();
        }
    }

}
