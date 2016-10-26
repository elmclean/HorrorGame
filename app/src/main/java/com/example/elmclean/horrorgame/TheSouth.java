package com.example.elmclean.horrorgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TheSouth extends AppCompatActivity {
    public int messageIndex = 0;
    List<String> message = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_south);

        message.add("Viola looks around and finds the way is blocked by a startlingly huge patch of roses.");
        message.add("There is a small path that leads through the woods to the left. Viola walks carefully through the foliage and finds an axe lodged in a tree.");

        TextView dialogBox = (TextView) findViewById(R.id.storyText);
        dialogBox.setText(message.get(messageIndex));
    }

    public void nextDialog(View v) {
        messageIndex = messageIndex + 1;
        if(messageIndex < message.size()) {
            TextView dialogBox = (TextView) findViewById(R.id.storyText);
            dialogBox.setText(message.get(messageIndex));
        } else {
//            noting
        }
    }
}
