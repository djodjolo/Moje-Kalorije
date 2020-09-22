package com.ais.mojekalorije;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.ais.mojekalorije.model.Event;
import com.ais.mojekalorije.model.userInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;


import android.widget.DatePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user;

    ArrayList<String> arrayListaKcal;
    ArrayList<String> arrayListaDetails;
    ArrayList<String> arrayListaDeletes;

    ListView listViewKcal;
    ListView listViewDetail;
    ListView listViewDeletes;

    TextView textViewDateMain = null;

    DateTime today;
    int suma;

    DatePicker picker;
    userInfo userInfo;

    String dialogInputDescription;
    String dialogInputKcal;
    TextView brojKcal;


    private  void fetchData(String auid, int aday, int amonth, int ayear){
        today = new DateTime();
        final String uid = auid;
        final int day = aday;
        final int month = amonth;
        final int year = ayear;

        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            arrayListaKcal = new ArrayList<>();
                            arrayListaDetails = new ArrayList<>();
                            arrayListaDeletes = new ArrayList<>();
                            listViewKcal = (ListView) findViewById(R.id.listViewKcal);
                            listViewDetail = (ListView) findViewById(R.id.listViewHrana);
                            listViewDeletes = (ListView) findViewById(R.id.listViewDeletes);

                            //init suma na 0
                            suma = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Event eventModel = document.toObject(Event.class);

                                if (eventModel.getUser_id().equals(uid)
                                        && day == new DateTime(eventModel.getDate()).getDayOfMonth()
                                        && month == new DateTime(eventModel.getDate()).getMonthOfYear()
                                        && year == new DateTime(eventModel.getDate()).getYear()
                                ) {
                                    arrayListaKcal.add(eventModel.getKcal());
                                    arrayListaDetails.add(eventModel.getDescription());
                                    arrayListaDeletes.add(document.getId());
                                    suma += Integer.parseInt(eventModel.getKcal());
                                }
                            }

                            db.collection("user_info")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    userInfo uf = document.toObject(userInfo.class);
                                                    if (uf.getUid().equals(user.getUid())) {
                                                        brojKcal.setText("" + suma + "/" + uf.getKcal());
                                                    }
                                                }
                                            } else {
                                                Log.e("Poruka Exc", "Error getting documents.", task.getException());
                                            }
                                        }
                                    });
                            ArrayAdapter<String> adapterlistaKcal = new ArrayAdapter<String>(MainActivity.this, R.layout.row, arrayListaKcal);
                            ArrayAdapter<String> adapterlistaDetails = new ArrayAdapter<String>(MainActivity.this, R.layout.row, arrayListaDetails);

                            ArrayList<String> listaMinusa = new ArrayList();
                            for (String s : arrayListaDeletes) {
                                listaMinusa.add("X");
                            }
                            ArrayAdapter<String> adapterlistaDeletes = new ArrayAdapter<String>(MainActivity.this, R.layout.row, listaMinusa);

                            listViewKcal.setAdapter(adapterlistaKcal);
                            listViewDetail.setAdapter(adapterlistaDetails);
                            listViewDeletes.setAdapter(adapterlistaDeletes);


                            listViewDeletes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                                    db.collection("events").document(arrayListaDeletes.get(position)).delete().addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "Uspesno izbrisano!",
                                                        Toast.LENGTH_LONG).show();

                                                fetchData(user.getUid(),today.getDayOfMonth(),today.getMonthOfYear(),today.getYear());
                                            } else {
                                                Toast.makeText(MainActivity.this, "Neuspesno izbrisano!",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                }
                            });
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DateTime today = new DateTime();

        TextView usernameText = (TextView) findViewById(R.id.usernameText);
        brojKcal = (TextView) findViewById(R.id.brojKalorija);

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

       this.fetchData(user.getUid(),today.getDayOfMonth(),today.getMonthOfYear(),today.getYear());


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

                        dialogInputDescription = input.getText().toString();
                        AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
                        alert2.setTitle("Unesite  broj kalorija");
                        final EditText input = new EditText(MainActivity.this);
                        alert2.setView(input);

                        alert2.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialogInputKcal = input.getText().toString();

                                Map<String, Object> event = new HashMap<>();
                                event.put("date", new java.util.Date());
                                event.put("description", dialogInputDescription);
                                event.put("kcal", dialogInputKcal);
                                event.put("user_id",  user.getUid());

                                db.collection("events").document().set(event)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                MainActivity.this.fetchData(user.getUid(),today.getDayOfMonth(),today.getMonthOfYear(),today.getYear());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                return;
                                            }
                                        });
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

        TextView textViewSettingas = (TextView) findViewById(R.id.podesavanjaText);
        textViewSettingas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });




        // DATE STRING ON MAIN SCREEN

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        textViewDateMain = (TextView) findViewById(R.id.textViewDateMain);
        textViewDateMain.setText(date_n);

        textViewDateMain.setOnClickListener(new View.OnClickListener() {
                        @Override
                             public void onClick(View view) {
//
                                        AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                                        picker = new DatePicker(MainActivity.this);
                                        picker.setCalendarViewShown(false);
                                        builder3.setTitle("Izaberite datum");
                                        picker.setBackgroundColor(0x051C48);
                                        builder3.setView(picker);
                                        builder3.setPositiveButton("Izaberi", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                               textViewDateMain.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date(picker.getYear()-1900, picker.getMonth(), picker.getDayOfMonth())));
                                               MainActivity.this.fetchData(user.getUid(),picker.getDayOfMonth(),picker.getMonth()+1,picker.getYear());
                                               return;

                                            }
                                        });
                                        builder3.setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        return;
                                                    }
                                                });
                            builder3.show();

          }
         });

    }
}