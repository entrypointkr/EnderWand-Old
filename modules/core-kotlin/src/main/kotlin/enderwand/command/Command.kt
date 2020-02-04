package enderwand.command

import enderwand.command.executor.Executor
import enderwand.command.sender.Sender
import enderwand.command.tabcompleter.TabCompleter

/**
 * Created by JunHyung Lim on 2019-12-18
 */
interface Command<S : Sender> : Executor<S>, TabCompleter<S>