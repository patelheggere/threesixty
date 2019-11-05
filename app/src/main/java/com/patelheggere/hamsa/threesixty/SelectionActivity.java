package com.patelheggere.hamsa.threesixty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.patelheggere.hamsa.threesixty.chatbot.ChatBotActivity;

public class SelectionActivity extends AppCompatActivity {
    private Button buttonChatBot, buttonVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        buttonVideo = findViewById(R.id.three_sixty);
        buttonChatBot = findViewById(R.id.chatbot);

        buttonChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, ChatBotActivity.class));
            }
        });

        buttonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, MainActivity.class));
            }
        });
    }
}
