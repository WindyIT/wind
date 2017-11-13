package com.example.windy.wind.customtabs;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.windy.wind.R;

/**
 * Created by windy on 2017/11/13.
 */

public class CustomTabsHelper {
    public static void openUrl(Context context, String url) {
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //if (sharedPreferences.getBoolean("chrome_custom_tabs", true)) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            builder.build().launchUrl(context, Uri.parse(url));
//        //} else {
//            try {
//                context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(context, "No browser found", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}
