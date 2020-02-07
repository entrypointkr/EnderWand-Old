package kr.entree.enderwand;

import kr.entree.enderwand.collection.Reader;
import kr.entree.enderwand.command.Argument;
import kr.entree.enderwand.command.Command;
import kr.entree.enderwand.command.CommandBuilder;
import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.sender.ConsoleSender;
import kr.entree.enderwand.command.sender.Sender;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
        command.execute(new CommandContext<>(ConsoleSender.INSTANCE, Reader.of()));
        assertTrue(invoked.get());
    }

    Argument<Number> number() {
        return new Argument<>();
    }

    @Test
    public void composition() {
        String input = "1 plus 2";
        AtomicInteger result = new AtomicInteger();
        // TODO
    }
}
