package com.ais.mojekalorije;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button   loginButton = (Button) findViewById(R.id.buttonLoginLogin);

        mAuth = FirebaseAuth.getInstance();
//



       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               EditText username = (EditText) findViewById(R.id.editTextLoginUsername) ;
               EditText password = (EditText) findViewById(R.id.editTextLoginPassword);


                   mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                           .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       // Sign in success, update UI with the signed-in user's information
                                       FirebaseUser user = mAuth.getCurrentUser();

                                       startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                   } else {
                                       // If sign in fails, display a message to the user.
                                       Toast.makeText(LoginActivity.this, "Authentication failed.",
                                       Toast.LENGTH_SHORT).show();
                                   }

                               }
                           });
            }
       });


        Toolbar tb =  (Toolbar) findViewById(R.id.toolbarLogin);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            }
        });

        TextView textLoginRegister = (TextView) findViewById(R.id.textLoginRegister);
        textLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }
}
