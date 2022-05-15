package com.petme.app.utils;

import android.Manifest;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Consts {

    public static final String SHARED_PREF = "pet_me_prefs";


    public static List < String > getPerms ( ) {
        List < String > perms;

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ) {
            perms = new ArrayList <> ( Arrays.asList (
                    Manifest.permission.ACCESS_FINE_LOCATION ,
                    Manifest.permission.ACCESS_COARSE_LOCATION ,
                    Manifest.permission.INTERNET
            ) );
        } else if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ) {
            perms = new ArrayList <> ( Arrays.asList (
                    Manifest.permission.ACCESS_FINE_LOCATION ,
                    Manifest.permission.ACCESS_COARSE_LOCATION ,
                    Manifest.permission.INTERNET
            ) );
        } else {
            perms = new ArrayList <> ( Arrays.asList (
                    Manifest.permission.ACCESS_FINE_LOCATION ,
                    Manifest.permission.ACCESS_COARSE_LOCATION ,
                    Manifest.permission.INTERNET
            ) );
        }

        return perms;
    }
}
