package com.ais.mojekalorije;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        mAuth = FirebaseAuth.getInstance();
//        mAuth.createUserWithEmailAndPassword("aa@gmail.com", "1234567")
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.e("CreatedUser", "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.e("AAa", "createUserWithEmail:failure", task.getException());
//                        }
//                    }
//                });

        Toolbar tb =  (Toolbar) findViewById(R.id.toolbarRegister);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
            }
        });

        TextView textLoginRegister = (TextView) findViewById(R.id.textRegisterToLogin);
        textLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
