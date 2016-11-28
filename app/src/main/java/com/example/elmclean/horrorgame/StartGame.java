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

    // for hamburger menu
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

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
        message.add("Viola finds herself in the middle of a forest. Looking around she sees a small cat sitting on a tree stump. A rotten log sits to her left, full of bugs and moss.");

        // create music
        doBindService();

        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        music.putExtra("MUSIC_NAME", "warehouse");
        startService(music);

        // start dialog box
        showDialog();

        // create inventory menu
        mDrawerList = (ListView)findViewById(R.id.inventory_drawer);
        addDrawerItems();

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        //Create the actionbar drawer toggle
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name) {
            //called when a drawer has settled in a closed state
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            //called when a drawer is settled in an open state
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

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
            alert.setMessage("Which path do you want to take?");

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
            alert.setPositiveButton("South", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                    Intent intent = new Intent(getBaseContext(), TheSouth.class);
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

    private void addDrawerItems() {
        List<String> inventoryArray = gameInventory.getList();

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inventoryArray);
        mDrawerList.setAdapter(mAdapter);
    }
}
