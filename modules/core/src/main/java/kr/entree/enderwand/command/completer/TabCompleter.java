package kr.entree.enderwand.command.completer;

import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.sender.Sender;

import java.util.List;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public interface TabCompleter<S extends Sender> {
    List<String> tabComplete(CommandContext<S> ctx);
}
