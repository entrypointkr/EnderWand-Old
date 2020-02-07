package kr.entree.enderwand.command.completer;

import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.sender.Sender;

import java.util.List;
import java.util.Map;

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
        return null;
    }
}
