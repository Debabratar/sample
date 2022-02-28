package com.cxyzy.websocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), MessageListener {
    private val serverUrl = "http://50.192.137.251:3000/"
//    private val serverUrl = "http://50.192.137.251:3000/socket.io/socket.io.js"
//    private val serverUrl = "ws://192.168.18.145:8086/socketServer/abc"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WebSocketManager.init(serverUrl, this)
        connectBtn.setOnClickListener {
            thread {
                kotlin.run {
                    WebSocketManager.connect()
                }
            }
        }
        clientSendBtn.setOnClickListener {
            if (WebSocketManager.sendMessage("Client sends")) {
                addText("Client sends\n")
            }
        }
        closeConnectionBtn.setOnClickListener {
            WebSocketManager.close()
        }
    }

    override fun onConnectSuccess() {
        addText("connection succeeded\n")
    }

    override fun onConnectFailed() {
        addText("Connection failed\n")
    }

    override fun onClose() {
        addText("Closed successfully\n")
    }

    override fun onMessage(text: String?) {
        addText("receive message：$text\n")
    }

    private fun addText(text: String?) {
        runOnUiThread {
            contentEt.text.append(text)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WebSocketManager.close()
    }
}
