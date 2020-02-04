package kr.entree.enderwand.command.sender;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public interface Sender {
    void sendMessage(Object message);

    default void sendError(Object message) {
        sendMessage(message);
    }

    boolean hasPermission(Object node);

    boolean isOp();
}
