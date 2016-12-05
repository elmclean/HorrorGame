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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_north);

        Intent i = getIntent();
        gameInventory = (Inventory)i.getSerializableExtra("Inventory");
        message.add("Black Cat:\n\"Hmm...So the way out is blocked by roses.\"");
        message.add("Black Cat:\n\"You gonna go in?\"");
        message.add("Black Cat:\n\"Might as well if you can't leave.\"");

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
}
