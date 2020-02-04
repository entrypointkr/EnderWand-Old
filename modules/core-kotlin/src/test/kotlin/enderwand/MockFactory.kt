package enderwand

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import enderwand.command.sender.Sender

/**
 * Created by JunHyung Lim on 2020-01-09
 */
fun createSender(msgReceiver: (Any) -> Unit) = mock<Sender> {
    on { sendMessage(any()) } doAnswer {
        msgReceiver(it.arguments[0])
    }
    on { hasPermission(any()) } doReturn true
}