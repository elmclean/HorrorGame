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

        message.add("As you try to continue south you find the exit is also blocked by an impassable patch of rose bushes.");
        message.add("You only spot one small path to the side that you could fit through.");
        message.add("You follow this spall path through the foliage to a large tree. You see that there is a machete lodged in the bark of the tree.");
        message.add("Viola\n\"This doesn't look like it could cut the roses to the south, but I might be able to cut through the small patch in the north.\"");

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
            if(gameInventory.searchInventory("Machete")){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Which direction would you like to go?");

                alert.setPositiveButton("North", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        Intent intent = new Intent(getBaseContext(), CutRoses.class);
                        intent.putExtra("Inventory", gameInventory);
                        startActivity(intent);
                    }
                });
                alert.show();

            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Would you like to pick up the machete?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        gameInventory.addItem("Machete");
                        message.add("You pick up the machete.");
                        showDialog();
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        }
    }
}
