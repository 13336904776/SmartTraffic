package com.example.mrzhang.smarttraffic.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */
public class SpUtil {
    public static final String spname = "setting";
    public static void putSSP(Context mContext,String key,String s){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(spname, 0);
        sharedPreferences.edit().putString(key,s).commit();
    }

    public static void putBSP(Context mContext,String key,boolean b){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(spname, 0);
        sharedPreferences.edit().putBoolean(key,b).commit();
    }

    public static String getSSP(Context mContext,String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(spname, 0);
        return sharedPreferences.getString(key,"");
    }

    public static boolean getBSP(Context mContext,String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(spname, 0);
        return sharedPreferences.getBoolean(key,false);
    }
}
