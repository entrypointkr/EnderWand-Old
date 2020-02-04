package kr.entree.enderwand.command

import kr.entree.enderwand.command.executor.Executor
import kr.entree.enderwand.command.sender.Sender
import kr.entree.enderwand.command.tabcompleter.TabCompleter

/**
 * Created by JunHyung Lim on 2019-12-18
 */
interface Command<S : Sender> : Executor<S>, TabCompleter<S>