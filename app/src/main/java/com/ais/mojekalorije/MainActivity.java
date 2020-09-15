package com.ais.mojekalorije;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.ais.mojekalorije.model.Event;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;
import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user;

    ArrayList<String> arrayListaKcal;
    ArrayList<String> arrayListaDetails;
    ListView listViewKcal;
    ListView listViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DateTime today = new DateTime();
        Log.e( "Danasnji dan ", "dan: "+today.getDayOfMonth() +" mesec: "+today.getMonthOfYear() +" godina: "+today.getYear());


        TextView usernameText = (TextView) findViewById(R.id.usernameText);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // User is signed in
            String name = user.getDisplayName();
            String email = user.getEmail();
//          Uri photoUrl = user.getPhotoUrl();
            usernameText.setText(email);

            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        } else {
            // No user is signed in
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        }

        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            arrayListaKcal = new ArrayList<>();
                            arrayListaDetails = new ArrayList<>();
                            listViewKcal = (ListView) findViewById(R.id.listViewKcal);
                            listViewDetail = (ListView) findViewById(R.id.listViewHrana);

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Event eventModel = document.toObject(Event.class);

                                if(eventModel.getUser_id().equals(user.getUid())
                                    && today.getDayOfMonth()==new DateTime(eventModel.getDate()).getDayOfMonth()
                                    && today.getMonthOfYear()==new DateTime(eventModel.getDate()).getMonthOfYear()
                                    && today.getYear()==new DateTime(eventModel.getDate()).getYear()
                                ){
                                    arrayListaKcal.add(eventModel.getKcal());
                                    arrayListaDetails.add(eventModel.getDescription());
                                }
                            }

                            ArrayAdapter<String> adapterlistaKcal = new ArrayAdapter<String>(MainActivity.this, R.layout.row, arrayListaKcal);
                            ArrayAdapter<String> adapterlistaDetails= new ArrayAdapter<String>(MainActivity.this, R.layout.row, arrayListaDetails);

                            listViewKcal.setAdapter(adapterlistaKcal);
                            listViewDetail.setAdapter(adapterlistaDetails);

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


//        ListView lista = (ListView) findViewById(R.id.listViewKcal);
//        ArrayList<String> arrayLista = new ArrayList<>();
//        arrayLista.add("1");
//        arrayLista.add("2");
//        ArrayAdapter<String> adapterlistaObavestenja = new ArrayAdapter<String>(this,R.layout.row,arrayListaKcal);
//        lista.setAdapter(adapterlistaObavestenja);
//
//
//        ListView lista2 = (ListView) findViewById(R.id.listViewHrana);
//        ArrayList<String> arrayLista2 = new ArrayList<>();
//        arrayLista2.add("Ovsene");
//        arrayLista2.add("Punjena");
//        ArrayAdapter<String> adapterlistaObavestenja2 = new ArrayAdapter<String>(this,R.layout.row,arrayLista2);
//        lista2.setAdapter(adapterlistaObavestenja2);

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