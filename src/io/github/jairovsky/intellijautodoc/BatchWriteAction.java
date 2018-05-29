package io.github.jairovsky.intellijautodoc;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import org.fest.util.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

class BatchWriteAction extends WriteCommandAction {

    private static final String ACTION_NAME = "Insert javadocs";
    private final List<SimpleAction> actions;

    public BatchWriteAction(@Nullable Project project, SimpleAction... actions) {
        super(project, ACTION_NAME);
        this.actions = Lists.newArrayList(actions);
    }

    @Override
    protected void run(@NotNull Result result) throws Throwable {

        actions.forEach(SimpleAction::execute);
    }
}
