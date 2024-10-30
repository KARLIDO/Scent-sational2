package com.example.scentsational


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInButton: Button = findViewById(R.id.btnSignIn)
        val signUpButton: Button = findViewById(R.id.btnSignUp)

        signInButton.setOnClickListener {
            // Navigate to WebViewActivity with sign-in URL
            openWebView("https://lif-sa.co.za/index.php")
        }

        signUpButton.setOnClickListener {
            // Navigate to WebViewActivity with sign-up URL
            openWebView("https://lif-sa.co.za/index3.php")
        }
    }

    private fun openWebView(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }
}
