package com.ais.mojekalorije;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
