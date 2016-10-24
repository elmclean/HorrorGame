package com.example.elmclean.horrorgame;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

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

        Intent svc = new Intent(this, MusicService.class);
        svc.setAction("com.example.MusicService");
        startService(svc);
    }

    public void startGame(View v)
    {
        stopService(new Intent(MainActivity.this,MusicService.class));
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
    }
}
