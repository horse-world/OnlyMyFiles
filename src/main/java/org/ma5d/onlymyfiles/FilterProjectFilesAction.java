package org.ma5d.onlymyfiles;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FilterProjectFilesAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }

        // Get all files in the project
        VirtualFile[] files = VirtualFileManager.getInstance().getFileSystem("file").findFileByPath(project.getBasePath()).getChildren();

        // Filter logic
        List<VirtualFile> filteredFiles = new ArrayList<>();
        for (VirtualFile file : files) {
            if (shouldInclude(file)) {
                filteredFiles.add(file);
            }
        }

        // Display filtered files (example using JOptionPane)
        StringBuilder message = new StringBuilder("Filtered Files:\n");
        for (VirtualFile file : filteredFiles) {
            message.append(file.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }

    private boolean shouldInclude(VirtualFile file) {
        // Add your custom filter logic here
        // Example: Exclude files with .txt extension
        return !file.getName().endsWith(".txt");
    }
}
