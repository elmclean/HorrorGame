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

public class House2 extends AppCompatActivity {
    public int messageIndex = 0;
    List<String> message = new ArrayList<String>();
    Inventory gameInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house2);

        Intent i = getIntent();
        gameInventory = (Inventory)i.getSerializableExtra("Inventory");

        message.add("This room is dark, a dark puddle of unidentifiable liquid lies in the middle of the room.");
        message.add("Across the room Viola can see a note hanging from the stone wall.");
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
            alert.setMessage("What would you like to inspect?");

            alert.setNegativeButton("Puddle", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    message.add("Thank you for playing this small demo game.");
                    showDialog();
//                    Intent intent = new Intent(getBaseContext(), TheNorth.class);
//                    intent.putExtra("Inventory", gameInventory);
//                    startActivity(intent);
                }
            });
            alert.setPositiveButton("Note", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    message.add("Game Over. You died");
                    showDialog();
//                    Intent intent = new Intent(getBaseContext(), House2.class);
//                    intent.putExtra("Inventory", gameInventory);
//                    startActivity(intent);
                }
            });
            alert.show();
        }
    }
}
