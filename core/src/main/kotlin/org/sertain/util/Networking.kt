package org.sert2521.powerup.util

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread


/**
 *
 */
open class BaseUDPServer(port: Int) {
    private val socket = DatagramSocket(port)

    init {
        while (true) {
            val buf = ByteArray(PACKET_SIZE)
            val packet = DatagramPacket(buf, buf.size)

            socket.receive(packet)
            val message = String(packet.data).trim { it <= ' ' }

            thread { onMessage(message) }
        }
    }

    open fun onMessage(message: String) {}

    private companion object {
        const val PACKET_SIZE = 128
    }
}

/**
 *
 */
open class BaseTCPServer(port: Int) {
    private val server = ServerSocket(port)

    init {
        while (true) {
            val client = server.accept()

            thread { onClient(client) }
        }
    }

    open fun onClient(client: Socket) {}
}
