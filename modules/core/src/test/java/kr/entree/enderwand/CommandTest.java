package kr.entree.enderwand;

import kr.entree.enderwand.collection.Reader;
import kr.entree.enderwand.command.Command;
import kr.entree.enderwand.command.CommandBuilder;
import kr.entree.enderwand.command.CommandContext;
import kr.entree.enderwand.command.sender.ConsoleSender;
import kr.entree.enderwand.command.sender.Sender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

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
        Assertions.assertTrue(invoked.get());
    }
}
