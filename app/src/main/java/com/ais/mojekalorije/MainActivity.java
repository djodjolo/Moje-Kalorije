package com.ais.mojekalorije;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword("aa@gmail.com", "1234567")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("CreatedUser", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("AAa", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });

        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e( "Poruka",document.getId() + " => " + document.getData());
                            }
                        } else {
                                Log.e("Poruka Exc", "Error getting documents.", task.getException());
                        }
                    }
                });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //ADD BUTTON CLICK
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Unesite  sta ste jeli");
                final EditText input = new EditText(MainActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value2 = input.getText().toString();
                        AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
                        alert2.setTitle("Unesite  broj kalorija");
                        final EditText input = new EditText(MainActivity.this);
                        alert2.setView(input);

                        alert2.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String value2 = input.getText().toString();
                                return;

                            }
                        });
                        alert2.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                });

                        alert2.show();
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

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(date_n);


        ListView lista = (ListView) findViewById(R.id.listView);
        ArrayList<String> arrayLista = new ArrayList<>();
        arrayLista.add("1");
        arrayLista.add("2");
        ArrayAdapter<String> adapterlistaObavestenja = new ArrayAdapter<String>(this,R.layout.row,arrayLista);
        lista.setAdapter(adapterlistaObavestenja);


        ListView lista2 = (ListView) findViewById(R.id.listViewHrana);
        ArrayList<String> arrayLista2 = new ArrayList<>();
        arrayLista2.add("Ovsene");
        arrayLista2.add("Punjena");
        ArrayAdapter<String> adapterlistaObavestenja2 = new ArrayAdapter<String>(this,R.layout.row,arrayLista2);
        lista2.setAdapter(adapterlistaObavestenja2);

//        String[] countryArray = {"India", "Pakistan", "USA", "UK"};
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.row,R.id.textView, countryArray);
//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(adapter);


        TextView textViewSettingas = (TextView) findViewById(R.id.podesavanjaText);
        textViewSettingas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CalendarView cw = (CalendarView) findViewById(R.id.calendarView);
//                if(cw.getVisibility() == View.VISIBLE){
//                    cw.setVisibility(View.INVISIBLE);
//                }else if(cw.getVisibility() == View.INVISIBLE){
//                    cw.setVisibility(View.VISIBLE);
//                };
            }
        });




    }

}