package com.example.orygoolibrary;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "AndroidHivePref";
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;


    public SharedPrefManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveItem(String itemName, String item){
        editor.putString(itemName, item);
        editor.commit();
    }

    public void removeItem(String item){
        editor.remove(item);
        editor.commit();
    }

    public String getItem(String itemName){
        String item = pref.getString(itemName, "");
        return item;
    }

    public void reset() {
        editor.clear();
        editor.commit();
    }


}

