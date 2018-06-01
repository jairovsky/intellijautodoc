package io.github.jairovsky.intellijautodoc.action;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import org.fest.util.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BatchWriteAction extends WriteCommandAction {

    private final List<SimpleAction> actions;

    public BatchWriteAction(@Nullable Project project, String name,  SimpleAction... actions) {
        super(project, name);
        this.actions = Lists.newArrayList(actions);
    }

    @Override
    protected void run(@NotNull Result result) throws Throwable {

        actions.forEach(SimpleAction::execute);
    }
}
