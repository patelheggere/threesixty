package com.patelheggere.hamsa.threesixty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



public class KatexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katex);
       // katexView = findViewById(R.id.katex_text);
        String val = "$$ c = \\pm\\sqrt{a^2 + b^2} $$";
      //  katexView.setText(val);
    }
}
