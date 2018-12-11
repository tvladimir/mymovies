package com.academy.fundamentals.common;

import android.app.Activity;
import android.content.Intent;

import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.HashMap;
import java.util.Map;

public class NavigationHelper {

    public static  void StartActivity(Activity source, Activity dest, HashMap<String,Object> params){
        Intent intent=new Intent(source,dest.getClass());
        if(params!=null){
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                intent.putExtra(key,value.toString());

                // ...
            }
        }

        source.startActivity(intent);

    }

}
