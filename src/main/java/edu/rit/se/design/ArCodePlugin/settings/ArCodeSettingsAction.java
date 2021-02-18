package edu.rit.se.design.ArCodePlugin.settings;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ArCodeSettingsAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        new ArCodeSettingsDialogWrapper(e.getProject(), true).showAndGet();
    }
}
