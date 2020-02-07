package kr.entree.enderwand;

import kr.entree.enderwand.command.*;
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
public class CommandTest {
    @Test
    public void simple() {
        AtomicBoolean invoked = new AtomicBoolean();
        Command<Sender> command = new CommandBuilder<>()
                .executes(ctx -> invoked.set(true))
                .build();
        command.execute(new CommandContext<>(ConsoleSender.INSTANCE, StringReader.ofEmpty()));
        assertTrue(invoked.get());
    }

    Argument<Number> number() {
        return new Argument<>();
    }

    @Test
    public void composition() {
        String input = "1 plus 2";
        AtomicInteger result = new AtomicInteger();
        new CommandBuilder<>()
                .child(number(), new CommandBuilder<>()
                        .child("plus", new CommandBuilder<>()
                                .child(number(), new CommandBuilder<>()
                                        .executes(ctx -> {
                                            result.set(ctx.getInt(0) + ctx.getInt(1));
                                        }))))
                .build()
                .execute(new CommandContext<>(ConsoleSender.INSTANCE, StringReader.of(input)));
        assertEquals(result.get(), 3);
    }
}
