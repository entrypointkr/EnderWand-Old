package kr.entree.enderwand.command;

import kr.entree.enderwand.collection.Lists;
import kr.entree.enderwand.command.sender.Sender;
import org.junit.jupiter.api.Test;

import static kr.entree.enderwand.command.Factory.contextConsoleOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by JunHyung Lim on 2020-02-08
 */
public class TabCompleterTest {
    private final Command<Sender> command = new CommandBuilder<>()
            .child("a", new CommandBuilder<>())
            .child("abc", new CommandBuilder<>())
            .child("deep", new CommandBuilder<>()
                    .child("a", new CommandBuilder<>())
                    .child("abc", new CommandBuilder<>()))
            .build();

    @Test
    public void map() {
        String input = "";
        assertEquals(
                Lists.of("a", "abc"),
                command.tabComplete(contextConsoleOf(input))
        );
    }

    @Test
    public void map2() {
        String input = "ab";
        assertEquals(
                Lists.of("abc"),
                command.tabComplete(contextConsoleOf(input))
        );
    }

    @Test
    public void map3() {
        String input = "abcd";
        assertEquals(
                Lists.of(),
                command.tabComplete(contextConsoleOf(input))
        );
    }

    @Test
    public void mapDeep() {
        String input = "deep ";
        assertEquals(
                Lists.of(),
                command.tabComplete(contextConsoleOf(input))
        );
    }
}
