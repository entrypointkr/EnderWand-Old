package kr.entree.enderwand.command.executor;

import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.sender.Sender;

import java.util.Map;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class MapExecutor<S extends Sender, T extends CommandExecutor<S>> implements CommandExecutor<S> {
    private final Map<String, T> map;
    private final CommandExecutor<S> defaultExecutor;

    public MapExecutor(Map<String, T> map, CommandExecutor<S> defaultExecutor) {
        this.map = map;
        this.defaultExecutor = defaultExecutor;
    }

    @Override
    public void execute(CommandContext<S> ctx) {
        // TODO
    }
}
