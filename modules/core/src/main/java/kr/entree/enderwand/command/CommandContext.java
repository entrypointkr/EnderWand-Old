package kr.entree.enderwand.command;

import kr.entree.enderwand.command.sender.Sender;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class CommandContext<S extends Sender, T> {
    private final Sender sender;
    private final T args;

    public CommandContext(Sender sender, T args) {
        this.sender = sender;
        this.args = args;
    }

    public Sender sender() {
        return sender;
    }

    public T args() {
        return args;
    }
}
