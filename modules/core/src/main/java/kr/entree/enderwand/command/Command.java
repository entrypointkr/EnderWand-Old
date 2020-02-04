package kr.entree.enderwand.command;

import kr.entree.enderwand.command.completer.TabCompleter;
import kr.entree.enderwand.command.executor.CommandExecutor;
import kr.entree.enderwand.command.sender.Sender;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public interface Command<S extends Sender> extends CommandExecutor<S>, TabCompleter<S> {
}
