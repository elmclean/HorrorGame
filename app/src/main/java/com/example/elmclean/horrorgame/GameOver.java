package com.example.elmclean.horrorgame;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/umeplus-gothic.ttf");
        TextView titleText = (TextView) findViewById(R.id.title);
        Button newGame = (Button) findViewById(R.id.new_game);
        Button resume = (Button) findViewById(R.id.resume);

        doBindService();

        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        music.putExtra("MUSIC_NAME", "lost_chair");
        startService(music);
    }

    public void startGame(View v)
    {
        doUnbindService();

        stopService(new Intent(GameOver.this,MusicService.class));
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
    }

    public void resume() {
        // TODO start over at house 1
    }

    public void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
}
