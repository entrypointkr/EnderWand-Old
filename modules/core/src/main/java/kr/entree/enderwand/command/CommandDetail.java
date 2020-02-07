package kr.entree.enderwand.command;

import kr.entree.enderwand.command.argument.Argument;

import java.util.List;

/**
 * Created by JunHyung Lim on 2020-02-04
 */
public interface CommandDetail {
    String description();

    String permission();

    List<Argument<?>> arguments();
}
