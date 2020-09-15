package com.ais.mojekalorije;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Toolbar tb =  (Toolbar) findViewById(R.id.toolbar);
        Button buttonSettingsSave = (Button) findViewById(R.id.buttonSettingsSave);
        Button buttonLogout = (Button) findViewById(R.id.buttonLogout);


        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
            }
        });

        buttonSettingsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText input = new EditText(SettingsActivity.this);


                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                alert.setTitle("Old password");
                alert.setView(input);

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value2 = input.getText().toString();
                       return;
                    }
                });
                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.show();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                alert.setTitle("Da li ste sigurni?");

                alert.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            // User is signed in
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SettingsActivity.this, WelcomeActivity.class));
                        } else {
                            // No user is signed in
                        }
                        return;
                    }
                });
                alert.setNegativeButton("NE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.show();
            }
        });

    }

}