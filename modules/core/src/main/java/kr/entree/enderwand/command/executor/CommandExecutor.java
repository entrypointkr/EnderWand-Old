package kr.entree.enderwand.command.executor;

import kr.entree.enderwand.collection.Reader;
import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.sender.Sender;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public interface CommandExecutor<S extends Sender> {
    void execute(CommandContext<S, Reader<String>> ctx);
}
