package com.example.elmclean.horrorgame;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartGame extends AppCompatActivity {

    public Inventory gameInventory = new Inventory();

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
        setContentView(R.layout.activity_start_game);

        gameInventory.addItem("Letter");

        doBindService();

        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        music.putExtra("MUSIC_NAME", "warehouse");
        startService(music);
    }

    public void toTheNorth(View v)
    {
        if(gameInventory.searchInventory("Machete")) {
            Intent intent = new Intent(this, TheNorth.class);
            startActivity(intent);
        } else {
            LinearLayout buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

            TextView story = (TextView) findViewById(R.id.storyText);
            story.setText(getString(R.string.blocked_roses));

            Button northPath = (Button) findViewById(R.id.north);
            buttonLayout.removeView(northPath);
        }
    }

    public void toTheSouth(View v)
    {
        Intent intent = new Intent(this, TheSouth.class);
        startActivity(intent);
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
