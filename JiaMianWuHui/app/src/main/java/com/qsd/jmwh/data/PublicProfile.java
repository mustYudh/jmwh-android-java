package com.qsd.jmwh.data;

import android.content.Context;

import com.qsd.jmwh.APP;

import java.io.Serializable;


public class PublicProfile implements Serializable {

    private static PublicProfile instance;

    private static final String SHARE_PREFERENCES_NAME = ".public_profile";


    private SharedPreferencesHelper spHelper;


    private PublicProfile() {
        spHelper = SharedPreferencesHelper.create(
                APP.getInstance().getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE));
    }

    public synchronized static PublicProfile getInstance() {
        if (instance == null) {
            synchronized (PublicProfile.class) {
                if (instance == null) {
                    instance = new PublicProfile();
                }
            }
        }
        return instance;
    }


    public void clean() {
        spHelper.clear();
    }
}
