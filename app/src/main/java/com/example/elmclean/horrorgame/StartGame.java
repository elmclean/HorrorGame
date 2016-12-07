package com.example.elmclean.horrorgame;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StartGame extends AppCompatActivity {
    // for game mechanics
    public Inventory gameInventory = new Inventory();
    public int messageIndex = 0;
    public int choiceIndex = 0;
    List<String> message = new ArrayList<String>();

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
        setContentView(R.layout.activity_start_game);

        gameInventory.addItem("Letter");

        message.add("You find yourself in the middle of a forest. Looking around you see a small black cat sitting on a tree stump. A rotten log sits to your left, full of bugs and moss.");
        message.add("Black Cat\n\"You're up huh?\"");
        message.add("Viola\n\"I'm in a...forest?\"");
        message.add("You start to rummage through your pockets.");
        message.add("Viola\n\"There's a letter from dad.\"");
        message.add("Dad\n\"I don't mind if you go to her house, but just stay away from the forest. Hope to see you soon.\"");
        message.add("You walk up to read the sign post in the center of the clearing.\nNorth: ....'s House\nSouth: Out of the forest");

        // create music as a new intent/server
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        music.putExtra("MUSIC_NAME", "rumor");
        startService(music);

        // display text in dialog box
        showDialog();
    }

    // change the text in the dialog box at every click
    public void showDialog() {
        TextView dialogBox = (TextView) findViewById(R.id.storyText);
        dialogBox.setText(message.get(messageIndex));  // change text
        messageIndex = messageIndex + 1;
    }

    public void nextDialog(View v) {
        final TextView dialogBox = (TextView) findViewById(R.id.storyText);

        if(messageIndex < message.size()) {
            showDialog();  // display new text
        } else {  // no dialog text to put on the screen

            // build an alert to see what the player would like to do next
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Which path do you want to take?");

            // right button
            alert.setNegativeButton("North", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (gameInventory.searchInventory("Machete")) {
                        dialog.dismiss();

                        Intent intent = new Intent(getBaseContext(), TheNorth.class);
                        intent.putExtra("Inventory", gameInventory);
                        startActivity(intent);
                    } else {
                        dialogBox.setText("The path is blocked by a small patch of roses.");
                    }
                }
            });
            // left button
            alert.setPositiveButton("South", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                    doUnbindService();  // stop music

                    // go to next screen
                    Intent intent = new Intent(getBaseContext(), TheSouth.class);
                    intent.putExtra("Inventory", gameInventory);
                    startActivity(intent);
                }
            });
            alert.show();
        }
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
