package com.stalnobcrs.gounphish;

/**
 * Created by JoHNnY on 01-03-2018.
 */
import  android.content.Context;//Interface to global information about an application environment.
import  android.content.SharedPreferences;//Interface for accessing and modifying preference data returned by getSharedPreferences(String, int).
public class  Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;//Modifications to the preferences must go through an SharedPreferences.Editor object
    Context ctx;

    public Session(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin) {
        editor.putBoolean("loggedInmode", logggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }
}