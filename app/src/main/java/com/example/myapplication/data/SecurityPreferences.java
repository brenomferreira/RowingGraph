package com.example.myapplication.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SecurityPreferences {

    private SharedPreferences mSharedPreferences;

    // Construtor
    public SecurityPreferences(Context mContext) {
        this.mSharedPreferences = mContext.getSharedPreferences("Base de dados", Context.MODE_PRIVATE);
    }

   public void getStoredFloat(String key){
        this.mSharedPreferences.getFloat(key, 0.01f);
    }

    public void storeFloat(String key, float value){
        this.mSharedPreferences.edit().putFloat(key, value).apply();
    }


}
