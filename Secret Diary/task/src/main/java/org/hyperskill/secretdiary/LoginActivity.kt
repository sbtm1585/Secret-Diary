package org.hyperskill.secretdiary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.secretdiary.databinding.ActivityLoginBinding
import org.hyperskill.secretdiary.databinding.ActivityMainBinding

const val PIN = "1234"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val logInButton = binding.btnLogin
        val pinEntered = binding.etPin

        logInButton.setOnClickListener {
            if (pinEntered.text.toString() == PIN) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                pinEntered.error = getString(R.string.wrong_pin)
            }
        }
    }
}