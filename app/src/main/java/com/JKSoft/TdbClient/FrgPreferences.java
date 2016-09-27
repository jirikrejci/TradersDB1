package com.JKSoft.TdbClient;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.jirka.TdbClient.R;

/**
 * Created by Jirka on 27.9.2016.
 */
public class FrgPreferences extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
}


