package kr.entree.enderwand.command;

import kr.entree.enderwand.command.completer.MapCompleter;
import kr.entree.enderwand.command.executor.MapExecutor;
import kr.entree.enderwand.command.sender.Sender;

import java.util.List;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class MapCommand<S extends Sender> implements Command<S> {
    private final MapExecutor<S, Command<S>> executor;
    private final MapCompleter<S, Command<S>> tabCompleter;

    public MapCommand(MapExecutor<S, Command<S>> executor, MapCompleter<S, Command<S>> tabCompleter) {
        this.executor = executor;
        this.tabCompleter = tabCompleter;
    }

    @Override
    public void execute(CommandContext<S> ctx) {
        executor.execute(ctx);
    }

    @Override
    public List<String> tabComplete(CommandContext<S> ctx) {
        return tabCompleter.tabComplete(ctx);
    }
}
