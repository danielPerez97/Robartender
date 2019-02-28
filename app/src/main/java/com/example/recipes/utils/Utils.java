package com.example.recipes.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class Utils
{
    public static void startActivity(Activity activity, Class start)
    {
        Intent intent = new Intent(activity, start);
        activity.startActivity(intent);
    }

    public static void log(String message)
    {
        Log.d("DEBUGGING", message);
    }
}
