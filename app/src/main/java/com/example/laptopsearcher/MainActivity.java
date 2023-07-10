package com.example.laptopsearcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    BottomNavigationView navbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the GridView
        GridView laptopGridView = findViewById(R.id.laptopGridView);

        navbar = findViewById(R.id.navbar);
        navbar.setSelectedItemId(R.id.home);

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        // Start MainActivity
                        Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        break;

                    case R.id.map:
                        Toast.makeText(MainActivity.this, "Maps", Toast.LENGTH_SHORT).show();
                        Intent mapIntent = new Intent(MainActivity.this, NavigationActivity.class);
                        startActivity(mapIntent);
                        break;

                    default:
                }

                return true;
            }
        });

        // Perform HTTP request in a separate thread
        new Thread(() -> {
            try {
                // Perform the HTTP request
                URL url = new URL("https://www.techhypermart.com/notebooks?sort=p.price&order=ASC&filter=&limit=200");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the HTML content
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                    reader.close();

                    // Parse the HTML and extract laptop information
                    Document document = Jsoup.parse(content.toString());

                    // Extract laptop names
                    List<String> laptopNames = new ArrayList<>();
                    Elements laptopNameElements = document.select(".caption a");
                    for (Element nameElement : laptopNameElements) {
                        String laptopName = nameElement.text();
                        laptopNames.add(laptopName);
                    }

                    // Extract laptop prices
                    List<String> laptopPrices = new ArrayList<>();
                    Elements laptopPriceElements = document.select(".price-new");
                    for (Element priceElement : laptopPriceElements) {
                        String laptopPrice = priceElement.text();
                        laptopPrices.add(laptopPrice);
                    }

                    // Extract laptop image URLs
                    List<String> laptopImageUrls = new ArrayList<>();
                    Elements laptopImageElements = document.select(".image img");
                    for (Element imageElement : laptopImageElements) {
                        String imageUrl = imageElement.attr("src");
                        laptopImageUrls.add(imageUrl);
                    }

                    // Extract laptop link URLs
                    List<String> laptopLinkUrls = new ArrayList<>();
                    Elements laptopLinkElements = document.select(".image a");
                    for (Element linkElement : laptopLinkElements) {
                        String linkUrl = linkElement.attr("href");
                        laptopLinkUrls.add(linkUrl);
                    }

                    // Create Laptop objects
                    List<Laptop> laptopList = new ArrayList<>();
                    for (int i = 0; i < laptopNames.size(); i++) {
                        Laptop laptop = new Laptop(laptopNames.get(i), laptopPrices.get(i), laptopImageUrls.get(i), laptopLinkUrls.get(i));
                        laptopList.add(laptop);
                    }

                    // Update the UI on the main thread
                    runOnUiThread(() -> {
                        // Initialize and set the LaptopAdapter
                        LaptopAdapter laptopAdapter = new LaptopAdapter(MainActivity.this, laptopList);
                        laptopGridView.setAdapter(laptopAdapter);
                    });

                } else {
                    Log.e(TAG, "HTTP request failed with response code: " + responseCode);
                }

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }
}