package com.ais.mojekalorije;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button buttonWelcomeLogin = (Button) findViewById(R.id.buttonWelcomeLogin);
        Button buttonWelcomeRegister = (Button) findViewById(R.id.buttonWelcomeRegister);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));

        }

        buttonWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        buttonWelcomeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });
    }
}
