package kr.entree.enderwand.command;

import kr.entree.enderwand.command.sender.Sender;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class CommandContext<S extends Sender> {
    @Getter
    private final S sender;
    @Getter
    private final StringReader reader;
    @Getter
    @Setter
    private CommandArguments arguments = CommandArguments.EMPTY;

    public CommandContext(S sender, StringReader reader) {
        this.sender = sender;
        this.reader = reader;
    }
}
