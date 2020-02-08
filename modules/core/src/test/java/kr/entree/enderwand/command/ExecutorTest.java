package kr.entree.enderwand.command;

import kr.entree.enderwand.command.Command;
import kr.entree.enderwand.command.CommandBuilder;
import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.StringReader;
import kr.entree.enderwand.command.argument.Arguments;
import kr.entree.enderwand.command.sender.ConsoleSender;
import kr.entree.enderwand.command.sender.Sender;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class ExecutorTest {
    @Test
    public void simple() {
        AtomicBoolean invoked = new AtomicBoolean();
        Command<Sender> command = new CommandBuilder<>()
                .executes(ctx -> invoked.set(true))
                .build();
        command.execute(new CommandContext<>(ConsoleSender.INSTANCE, StringReader.ofEmpty()));
        assertTrue(invoked.get());
    }

    @Test
    public void composition() {
        String input = "1 plus 2";
        AtomicInteger result = new AtomicInteger();
        new CommandBuilder<>()
                .child(Arguments.ints("int"), new CommandBuilder<>()
                        .child("plus", new CommandBuilder<>()
                                .child(Arguments.ints("int"), new CommandBuilder<>()
                                        .executes(ctx -> {
                                            result.set(ctx.getInt(0) + ctx.getInt(1));
                                        }))))
                .build()
                .execute(new CommandContext<>(ConsoleSender.INSTANCE, StringReader.of(input)));
        assertEquals(result.get(), 3);
    }

    @Test
    public void executeDefault() {
        String input = "";
        AtomicInteger result = new AtomicInteger();
        new CommandBuilder<>()
                .executes(ctx -> result.set(1))
                .child("abc", new CommandBuilder<>()
                        .executes(ctx -> {
                            throw new IllegalStateException("Wrong place.");
                        }))
                .build()
                .execute(new CommandContext<>(ConsoleSender.INSTANCE, StringReader.of(input)));
        assertEquals(result.get(), 1);
    }
}
