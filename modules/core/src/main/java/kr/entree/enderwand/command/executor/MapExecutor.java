package kr.entree.enderwand.command.executor;

import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.StringReader;
import kr.entree.enderwand.command.sender.Sender;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class MapExecutor<S extends Sender, T extends CommandExecutor<S>> implements CommandExecutor<S> {
    private final Map<String, T> map;
    private final CommandExecutor<S> defaultExecutor;

    public MapExecutor(@NotNull Map<String, T> map, @NotNull CommandExecutor<S> defaultExecutor) {
        this.map = map;
        this.defaultExecutor = defaultExecutor;
    }

    private CommandExecutor<S> getExecutor(StringReader reader) {
        val input = reader.readOrNull();
        val sub = input != null ? map.get(input) : null;
        return sub != null ? sub : defaultExecutor;
    }

    @Override
    public void execute(CommandContext<S> ctx) {
        val executor = getExecutor(ctx.getReader());
        executor.execute(ctx);
    }
}
