package io.github.jairovsky.intellijautodoc.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

/**
 * A {@link com.intellij.openapi.project.DumbAwareAction} that shows/hides
 * based on a boolean condition.
 */
public abstract class HideableAction extends DumbAwareAction {

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        e.getPresentation().setVisible(isVisible(e));
    }

    protected abstract Boolean isVisible(AnActionEvent event);
}
