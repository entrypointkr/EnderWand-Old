package kr.entree.enderwand.command;

import kr.entree.enderwand.collection.Lists;
import kr.entree.enderwand.command.sender.Sender;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import java.util.List;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class CommandContext<S extends Sender> implements List<Object> {
    @Getter
    private final S sender;
    @Getter
    private final StringReader reader;
    @Getter
    @Setter
    @Delegate
    private List<Object> arguments = Lists.of();

    public CommandContext(S sender, StringReader reader) {
        this.sender = sender;
        this.reader = reader;
    }

    public Number getNumber(int index) {
        return ((Number) arguments.get(index));
    }

    public int getInt(int index) {
        return getNumber(index).intValue();
    }
}
