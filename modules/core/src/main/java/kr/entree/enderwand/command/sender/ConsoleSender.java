package kr.entree.enderwand.command.sender;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public class ConsoleSender implements Sender {
    public static final ConsoleSender INSTANCE = new ConsoleSender();

    private ConsoleSender() {
    }

    @Override
    public void sendMessage(Object message) {
        System.out.println(message);
    }

    @Override
    public boolean hasPermission(Object node) {
        return true;
    }

    @Override
    public boolean isOp() {
        return true;
    }
}
