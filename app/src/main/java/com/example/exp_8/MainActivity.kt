package com.example.exp_8

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var layoutHome: LinearLayout
    private lateinit var layoutAbout: LinearLayout
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        layoutHome = findViewById(R.id.layoutHome)
        layoutAbout = findViewById(R.id.layoutAbout)
        webView = findViewById(R.id.webView)

        // Setup WebView
        setupWebView()

        // Handle Back Press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.visibility == View.VISIBLE && webView.canGoBack()) {
                    webView.goBack()
                } else if (layoutHome.visibility != View.VISIBLE) {
                    showHome()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient() // Ensures links open in the app
        webView.loadUrl("https://www.google.com")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                showHome()
                true
            }
            R.id.action_website -> {
                showWebView()
                true
            }
            R.id.action_about -> {
                showAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showHome() {
        layoutHome.visibility = View.VISIBLE
        webView.visibility = View.GONE
        layoutAbout.visibility = View.GONE
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun showWebView() {
        layoutHome.visibility = View.GONE
        webView.visibility = View.VISIBLE
        layoutAbout.visibility = View.GONE
        supportActionBar?.title = "Website"
    }

    private fun showAbout() {
        layoutHome.visibility = View.GONE
        webView.visibility = View.GONE
        layoutAbout.visibility = View.VISIBLE
        supportActionBar?.title = "About"
    }
}
