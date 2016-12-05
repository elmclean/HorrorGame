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

public class House1 extends AppCompatActivity {
    public int messageIndex = 0;
    List<String> message = new ArrayList<String>();
    Inventory gameInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house1);

        Intent i = getIntent();
        gameInventory = (Inventory)i.getSerializableExtra("Inventory");

        message.add("The first room is small, and decorated simply. Candles light the room, the candle's flames sway curiously.");
        message.add("Viola only sees one door leading into another room.");
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
            alert.setMessage("Go through the door?");

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                    Intent intent = new Intent(getBaseContext(), House2.class);
                    intent.putExtra("Inventory", gameInventory);
                    startActivity(intent);
                }
            });
            alert.show();
        }
    }
}
