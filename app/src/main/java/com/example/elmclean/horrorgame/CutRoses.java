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

public class CutRoses extends AppCompatActivity {
    public int messageIndex = 0;
    List<String> message = new ArrayList<String>();
    Inventory gameInventory;
    Boolean cutRoses = false;

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
        setContentView(R.layout.activity_cut_roses);

        Intent i = getIntent();
        gameInventory = (Inventory)i.getSerializableExtra("Inventory");

        message.add("The path is blocked by a small patch of roses.");

        showDialog();
    }

    public void showDialog() {
        TextView dialogBox = (TextView) findViewById(R.id.storyText);
        dialogBox.setText(message.get(messageIndex));
        messageIndex = messageIndex + 1;
    }

    public void nextDialog(View v) {
        final TextView dialogBox = (TextView) findViewById(R.id.storyText);

        if (messageIndex < message.size()) {
            showDialog();
        } else {
            if (!cutRoses) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Cut the roses?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        message.add("Viola uses the machete to chop through the roses.");
                        cutRoses = true;
                        showDialog();
                        dialog.dismiss();
                    }
                });
                alert.show();
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Would you like to continue north?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        Intent intent = new Intent(getBaseContext(), TheNorth.class);
                        startActivity(intent);
                    }
                });
                alert.show();
            }
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