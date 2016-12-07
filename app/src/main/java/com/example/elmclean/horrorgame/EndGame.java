package com.example.elmclean.horrorgame;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class EndGame extends AppCompatActivity {
    // for music
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
        setContentView(R.layout.activity_end_game);

        // create music as a new intent/server
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        music.putExtra("MUSIC_NAME", "undermine");
        startService(music);
    }

    // bind the music to this activity
    public void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }


    // unbind the music from this activity
    public void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
}
