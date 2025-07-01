package com.example.fooddeli.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.fooddeli.Domain.FoodDomain;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;


public class TinyDB {
    private SharedPreferences preferences;
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    private String lastImagePath = "";

    public TinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }


    public ArrayList<FoodDomain> getListObject(String key) {
        Gson gson = new Gson();
        ArrayList<String> objStrings = getListString(key);
        ArrayList<FoodDomain> objects = new ArrayList<>();

        for (String jObjString : objStrings) {
            FoodDomain obj = gson.fromJson(jObjString, FoodDomain.class);
            objects.add(obj);
        }
        return objects;
    }


    public void putListObject(String key, ArrayList<FoodDomain> objArray) {
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<>();
        for (FoodDomain obj : objArray) {
            objStrings.add(gson.toJson(obj));
        }
        putListString(key, objStrings);
    }


    public ArrayList<String> getListString(String key) {
        return new ArrayList<>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }


    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[0]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }


    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }


    public void putString(String key, String value) {
        checkForNullKey(key);
        preferences.edit().putString(key, value).apply();
    }
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        checkForNullKey(key);
        preferences.edit().putBoolean(key, value).apply();
    }
    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }
} 