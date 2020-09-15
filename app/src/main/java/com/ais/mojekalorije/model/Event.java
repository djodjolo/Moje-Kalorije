package com.ais.mojekalorije.model;

//import java.sql.Timestamp;
import java.util.Date;



public class Event {
    String description,kcal,user_id;
    Date date;


    public String getKcal() {
        return kcal;
    }

    @Override
    public String toString() {
        return "Event{" +
                "description='" + description + '\'' +
                ", kcal='" + kcal + '\'' +
                ", user_id='" + user_id + '\'' +
                ", date=" + date +
                '}';
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}