package com.example.elmclean.horrorgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class House2 extends AppCompatActivity {
    public int messageIndex = 0;
    List<String> message = new ArrayList<String>();
    Inventory gameInventory;
    Boolean note = false;
    Boolean complete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house2);

        Intent i = getIntent();
        gameInventory = (Inventory)i.getSerializableExtra("Inventory");

        message.add("This room is dark, a strange stain lies in the middle of the room.");
        message.add("Viola\n\"Gross.\"");
        message.add("Across the room you can see a note hanging from the stone wall.");
        message.add("There are no other doors, it seems to be a dead end.");
        showDialog();
    }

    public void showDialog() {
        TextView dialogBox = (TextView) findViewById(R.id.storyText);
        dialogBox.setText(message.get(messageIndex));
        messageIndex = messageIndex + 1;
    }

    public void nextDialog(View v) {
        final TextView dialogBox = (TextView) findViewById(R.id.storyText);
        final ImageView background = (ImageView) findViewById(R.id.background);

        if (messageIndex < message.size()) {
            showDialog();
        } else if(complete) {
            message.add("The Witch's House Demo ~ Fin");
            showDialog();
        } else if (note) {  // safe to look at the note now
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("What would you like to inspect?");

            alert.setPositiveButton("Note", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    message.add("Thank you for playing this small demo of The Witch's House. I encourage everyone to download and play the full game.");
                    message.add("The Witch's House Demo ~ Fin");
                    background.setImageResource(R.drawable.note);
                    complete = true;
                    showDialog();
                }
            });
            alert.show();
        } else { // not safe to look at the note
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("What would you like to inspect?");

            alert.setNegativeButton("Stain", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    background.setImageResource(R.drawable.stain);
                    message.add("The stain is deep red.");
                    message.add("Viola\n\"It looks like blood...or maybe paint? It's too dark to tell for sure.\"");
                    note = true;
                    showDialog();
                }
            });
            alert.setPositiveButton("Note", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    dialogBox.setVisibility(View.GONE);

                    gameOver();
                }
            });
            alert.show();
        }
    }

    public void gameOver() {
        final ImageView background = (ImageView) findViewById(R.id.background);

        new CountDownTimer(5000, 1000){
            int step = 0;

            @Override
            public void onTick(long miliseconds){
                if(step == 0) {
                    background.setImageResource(R.drawable.wall_1);
                    step = step + 1;
                } else if(step == 1) {
                    background.setImageResource(R.drawable.wall_2);
                    step = step + 1;
                } else if(step == 2) {
                    background.setImageResource(R.drawable.wall_3);
                    step = step + 1;
                } else if(step == 3) {
                    background.setImageResource(R.drawable.wall_4);
                }
            }

            @Override
            public void onFinish(){
                // after 5 seconds
                Intent intent = new Intent(getBaseContext(), GameOver.class);
                intent.putExtra("Inventory", gameInventory);
                startActivity(intent);
            }
        }.start();
    }
}
