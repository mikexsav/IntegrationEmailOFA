package ru.my.mailprovider

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.net.InetAddress

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val code = EmailVerification.generateCode()

            Thread {
                try {
                    Log.d("CrewloomAuth", "Начинаем отправку")
                    Log.d("CrewloomAuth", "Получатель: mmmsss717171@gmail.com")
                    Log.d("CrewloomAuth", "Код: $code")

                    EmailVerification.sendCode(
                        "mmmsss717171@gmail.com",
                        code
                    )

                    Log.d("CrewloomAuth", "Send status: SUCCESS")

                } catch (e: Exception) {
                    Log.e("CrewloomAuth", "Send status: FAILED")
                    Log.e("CrewloomAuth", "Message: ${e.message}")
                    Log.e("CrewloomAuth", "Cause: ${e.cause}")
                    Log.e("CrewloomAuth", "StackTrace:", e)
                }
            }.start()

            Thread {
                try {
                    val hosts = InetAddress.getAllByName("smtp.spaceweb.ru")

                    hosts.forEach {
                        Log.d(
                            "CrewloomAuth",
                            "IP smtp.spaceweb.ru: ${it.hostAddress}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("CrewloomAuth", "DNS ERROR", e)
                }
            }.start()
        }

    }
}