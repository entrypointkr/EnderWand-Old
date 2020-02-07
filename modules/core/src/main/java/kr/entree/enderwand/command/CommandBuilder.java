package kr.entree.enderwand.command;

import kr.entree.enderwand.collection.Sets;
import kr.entree.enderwand.command.argument.Argument;
import kr.entree.enderwand.command.completer.MapCompleter;
import kr.entree.enderwand.command.completer.TabCompleter;
import kr.entree.enderwand.command.executor.CommandExecutor;
import kr.entree.enderwand.command.executor.MapExecutor;
import kr.entree.enderwand.command.sender.Sender;
import lombok.Getter;
import lombok.val;

import java.util.*;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class CommandBuilder<S extends Sender> {
    @Getter
    private Set<String> aliases = new HashSet<>();
    @Getter
    private String description = "";
    @Getter
    private String permission = "";
    @Getter
    private List<Argument<?>> arguments = new ArrayList<>();
    private CommandExecutor<S> executor = null;
    private TabCompleter<S> completer = null;
    @Getter
    private final Map<String, Command<S>> childs = new HashMap<>();

    public CommandBuilder<S> aliases(Set<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandBuilder<S> aliases(String... aliases) {
        this.aliases = Sets.of(aliases);
        return this;
    }

    public CommandBuilder<S> description(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder<S> permission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder<S> child(String label, Command<S> command) {
        childs.put(label, command);
        return this;
    }

    public CommandBuilder<S> child(String label, CommandBuilder<S> builder) {
        Command<S> command = builder.build();
        child(label, command);
        for (String alias : builder.aliases) {
            child(alias, command);
        }
        return this;
    }

    public <T> CommandBuilder<S> child(Argument<T> argument, CommandBuilder<S> builder) {
        val command = builder.build();
        executes(ctx -> {
            val parsed = argument.parse(ctx.getReader());
            ctx.getArguments().add(parsed);
            command.execute(ctx);
        });
        return this;
    }

    public CommandBuilder<S> executes(CommandExecutor<S> executor) {
        this.executor = executor;
        return this;
    }

    public Command<S> build() {
        if (childs.isEmpty()) {
            return new StandardCommand<>(executor, completer, description, permission, arguments);
        } else {
            return new MapCommand<>(
                    new MapExecutor<>(childs, ctx -> {
                        throw new RuntimeException("");
                    }),
                    new MapCompleter<>(childs)
            );
        }
    }

    public static void main(String[] args) {
        Map<String, Number> map = new HashMap<>();
        map.getOrDefault("a", (Integer) 3);
    }
}
