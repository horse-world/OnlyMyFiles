package org.ma5d.onlymyfiles;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class ShowProjectNameAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getProject();
        Messages.showMessageDialog("项目名称为" + project.getName(), "这是标题", Messages.getInformationIcon());
    }
}
