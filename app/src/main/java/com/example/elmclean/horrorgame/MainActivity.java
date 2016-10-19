package com.example.elmclean.horrorgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/umeplus-gothic.ttf");
        TextView titleText = (TextView) findViewById(R.id.title);
        Button newGame = (Button) findViewById(R.id.new_game);
        Button resume = (Button) findViewById(R.id.resume);

        titleText.setTypeface(type);
        newGame.setTypeface(type);
        resume.setTypeface(type);
    }

    public void startGame(View v)
    {
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
    }

}
