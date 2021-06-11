package com.esgi.codesurvival.rabbit

import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch


@Component
class Receiver {
    val latch = CountDownLatch(1)

    fun receiveMessage(message: ByteArray) {
        println("Received <$String(message)>")
        latch.countDown()
    }
}