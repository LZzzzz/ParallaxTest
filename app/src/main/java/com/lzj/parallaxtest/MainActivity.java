package com.lzj.parallaxtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    private ParallaxListView parallaxListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parallaxListView = (ParallaxListView) findViewById(R.id.pl);
        parallaxListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Array.NAMES));
    }
}
