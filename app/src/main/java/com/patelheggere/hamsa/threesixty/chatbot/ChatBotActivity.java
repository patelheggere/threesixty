package com.patelheggere.hamsa.threesixty.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.patelheggere.hamsa.threesixty.R;

public class ChatBotActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());

                /*
                    WebSettings
                        Manages settings state for a WebView. When a
                        WebView is first created, it obtains a set
                        of default settings.

                    setJavaScriptEnabled(boolean flag)
                        Tells the WebView to enable JavaScript execution.
                 */
        webView.getSettings().setJavaScriptEnabled(true);

        // Get the Android assets folder path
        String folderPath = "file:android_asset/";

        // Get the HTML file name
        String fileName = "chatbot.html";

        // Get the exact file location
        String file = folderPath + fileName;

                /*
                    loadUrl(String url)
                        Loads the given URL.
                 */

        // Render the HTML file on WebView
        webView.loadUrl(file);
    }
}
