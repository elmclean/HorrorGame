package com.example.elmclean.horrorgame;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TheNorth extends AppCompatActivity {
    public int messageIndex = 0;
    List<String> message = new ArrayList<String>();
    Inventory gameInventory;
    Boolean resume = false;

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
        setContentView(R.layout.activity_the_north);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        gameInventory = (Inventory)i.getSerializableExtra("Inventory");

        if(extras.containsKey("Resume")) {
            resume = (Boolean)i.getSerializableExtra("Resume");
        }

        if(resume) {
            // create music
            doBindService();
            Intent music = new Intent();
            music.setClass(this, MusicService.class);
            music.putExtra("MUSIC_NAME", "rumor");
            startService(music);
        }
        message.add("Black Cat\n\"Hmm...So the way out is blocked by roses.\"");
        message.add("Black Cat\n\"You gonna go in?\"");
        message.add("Black Cat\n\"Might as well if you can't leave.\"");

        showDialog();
    }

    public void showDialog() {
        TextView dialogBox = (TextView) findViewById(R.id.storyText);
        dialogBox.setText(message.get(messageIndex));
        messageIndex = messageIndex + 1;
    }
    public void nextDialog(View v) {
        final TextView dialogBox = (TextView) findViewById(R.id.storyText);

        if(messageIndex < message.size()) {
            showDialog();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Enter the house?");

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                    Intent intent = new Intent(getBaseContext(), House1.class);
                    intent.putExtra("Inventory", gameInventory);
                    startActivity(intent);
                }
            });
            alert.show();
        }
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
