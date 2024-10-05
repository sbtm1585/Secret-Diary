package org.hyperskill.secretdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

const val PIN = "1234"

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val logInButton = findViewById<Button>(R.id.btnLogin)
        val pinEntered = findViewById<EditText>(R.id.etPin)

        logInButton.setOnClickListener {
            if (pinEntered.text.toString() == PIN) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                pinEntered.error = "Wrong PIN!"
            }
        }

    }
}