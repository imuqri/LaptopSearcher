package com.example.laptopsearcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity {

    BottomNavigationView navbar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navbar = findViewById(R.id.navbar);
        navbar.setSelectedItemId(R.id.map);
        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  // Enable JavaScript in the WebView
        webSettings.setGeolocationEnabled(true); // Enable geolocation in the WebView

        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                // Allow geolocation permission request
                callback.invoke(origin, true, false);
            }
        });

        // Load the Google search page for "computer shop near me"
        webView.loadUrl("https://www.google.com/maps/search/computer+shop+near+me");

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(NavigationActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        // Start MainActivity
                        Intent homeIntent = new Intent(NavigationActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        break;

                    case R.id.map:
                        Toast.makeText(NavigationActivity.this, "Maps", Toast.LENGTH_SHORT).show();
                        Intent mapIntent = new Intent(NavigationActivity.this, NavigationActivity.class);
                        startActivity(mapIntent);
                        break;

                    default:
                }

                return true;
            }
        });
    }
}