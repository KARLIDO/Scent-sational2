package com.example.scentsational

import android.app.Dialog
import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webView: WebView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient() // Ensures web content opens in the WebView
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed() // Ignore SSL certificate errors
            }
        }
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.loadUrl("https://lif-sa.co.za/")

        // Show rating popup after loading
        showRatingPopup()
    }

    private fun showRatingPopup() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_rating)

        val ratingBar = dialog.findViewById<RatingBar>(R.id.popupRatingBar)
        val btnRate = dialog.findViewById<Button>(R.id.btnRate)
        val btnRemindLater = dialog.findViewById<Button>(R.id.btnRemindLater)

        // Handle "Rate" button click
        btnRate.setOnClickListener {
            val rating = ratingBar.rating
            saveRatingToFirebase(rating)
            dialog.dismiss() // Close dialog
        }

        // Handle "Remind Me Later" button click
        btnRemindLater.setOnClickListener {
            dialog.dismiss() // Close dialog without saving
        }

        dialog.show()
    }

    private fun saveRatingToFirebase(rating: Float) {
        // Initialize Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        val ratingsRef = database.getReference("ratings")

        // Save rating with a unique ID
        val ratingId = ratingsRef.push().key // Generate unique ID for each rating
        if (ratingId != null) {
            ratingsRef.child(ratingId).setValue(rating)
                .addOnSuccessListener {
                    // Successfully saved
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }
}
