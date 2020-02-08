package kr.entree.enderwand.command.completer;

import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.sender.Sender;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class MapCompleter<S extends Sender, T extends TabCompleter<S>> implements TabCompleter<S> {
    private final Map<String, T> map;

    public MapCompleter(Map<String, T> map) {
        this.map = map;
    }

    @Override
    public List<String> tabComplete(CommandContext<S> ctx) {
        val reader = ctx.getReader();
        val input = reader.canPeek() ? reader.read() : "";
        if (reader.canPeek()) {
            val sub = map.get(input);
            return sub.tabComplete(ctx);
        } else {
            return map.keySet().stream()
                    .filter(alias -> alias.startsWith(input))
                    .collect(Collectors.toList());
        }
    }
}
