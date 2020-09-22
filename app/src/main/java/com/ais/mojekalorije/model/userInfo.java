package com.ais.mojekalorije.model;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.ais.mojekalorije.MainActivity;
import com.ais.mojekalorije.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class userInfo {
    String uid,lastMeal,kcal;
    boolean sixteeneight;

    @Override
    public String toString() {
        return "userInfo{" +
                "uid='" + uid + '\'' +
                ", lastMeal='" + lastMeal + '\'' +
                ", kcal='" + kcal + '\'' +
                ", sixteeneight=" + sixteeneight +
                '}';
    }

    public userInfo(){
    }


        public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLastMeal() {
        return lastMeal;
    }

    public void setLastMeal(String lastMeal) {
        this.lastMeal = lastMeal;
    }

    public boolean isSixteeneight() {
        return sixteeneight;
    }

    public void setSixteeneight(boolean sixteeneight) {
        this.sixteeneight = sixteeneight;
    }


}
