package kr.entree.enderwand.command;

import kr.entree.enderwand.collection.Lists;
import lombok.experimental.Delegate;

import java.util.List;

/**
 * Created by JunHyung Lim on 2020-02-07
 */
public class CommandArguments implements List<Object> {
    public static final CommandArguments EMPTY = new CommandArguments(Lists.of());

    @Delegate
    private final List<Object> arguments;

    public CommandArguments(List<Object> arguments) {
        this.arguments = arguments;
    }
}
