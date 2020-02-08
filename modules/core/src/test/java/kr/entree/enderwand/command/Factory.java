package kr.entree.enderwand.command;

import kr.entree.enderwand.command.sender.ConsoleSender;
import kr.entree.enderwand.command.sender.Sender;

/**
 * Created by JunHyung Lim on 2020-02-08
 */
public class Factory {
    public static CommandContext<Sender> contextConsoleOf(String input) {
        return new CommandContext<>(ConsoleSender.INSTANCE, StringReader.of(input));
    }
}
