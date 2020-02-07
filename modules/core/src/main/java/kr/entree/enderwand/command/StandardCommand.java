package kr.entree.enderwand.command;

import kr.entree.enderwand.command.argument.Argument;
import kr.entree.enderwand.command.completer.TabCompleter;
import kr.entree.enderwand.command.executor.CommandExecutor;
import kr.entree.enderwand.command.sender.Sender;

import java.util.List;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class StandardCommand<S extends Sender> implements Command<S>, CommandDetail {
    private final CommandExecutor<S> executor;
    private final TabCompleter<S> tabCompleter;
    private final String description;
    private final String permission;
    private final List<Argument<?>> arguments;

    public StandardCommand(
            CommandExecutor<S> executor,
            TabCompleter<S> tabCompleter,
            String description,
            String permission,
            List<Argument<?>> arguments
    ) {
        this.executor = executor;
        this.tabCompleter = tabCompleter;
        this.description = description;
        this.permission = permission;
        this.arguments = arguments;
    }

    @Override
    public List<String> tabComplete(CommandContext<S> ctx) {
        return tabCompleter.tabComplete(ctx);
    }

    @Override
    public void execute(CommandContext<S> ctx) {
        executor.execute(ctx);
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String permission() {
        return permission;
    }

    @Override
    public List<Argument<?>> arguments() {
        return arguments;
    }
}
