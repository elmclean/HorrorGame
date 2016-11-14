package com.example.elmclean.horrorgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TheSouth extends AppCompatActivity {
    public int messageIndex = 0;
    List<String> message = new ArrayList<String>();
    Inventory gameInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_south);

        Intent i = getIntent();
        gameInventory = (Inventory)i.getSerializableExtra("Inventory");

        message.add("Viola looks around and finds the exit is also blocked by a startlingly huge patch of roses.");
        message.add("There is a small path that leads through the woods to the left. Viola walks carefully through the foliage and finds an axe lodged in a tree.");

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
        } if(gameInventory.searchInventory("Machete")){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Which direction would you like to go?");

            alert.setPositiveButton("North", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.dismiss();
                }
            });
            alert.show();

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Would you like to pick up the axe?");

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    gameInventory.addItem("Machete");
                    dialog.dismiss();
                }
            });
            alert.show();
        }
    }
}
