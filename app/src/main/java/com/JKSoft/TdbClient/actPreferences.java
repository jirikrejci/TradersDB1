package com.JKSoft.TdbClient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jirka.TdbClient.R;

/**
 * Created by Jirka on 27.9.2016.
 */
public class actPreferences extends PreferenceActivity
{

    public static class FrgPreferences extends PreferenceFragment{
        SharedPreferences.OnSharedPreferenceChangeListener listener;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            addPreferencesFromResource(R.xml.preferences);  // Load the preferences from an XML resource
            SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();


            initSummary();

            listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    Log.e("JK", "Shared preferences inside fragment");

                    Preference preference = getPreferenceManager().findPreference(key);
                    updatePreferenceSummary(preference, key);
                }
            };

            sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        }

        public void initSummary () {
            PreferenceScreen preferenceScreen = getPreferenceScreen();

            for ( int i = 0; i <preferenceScreen.getPreferenceCount(); i++) {
                Preference preference = preferenceScreen.getPreference(i);
                if (preference instanceof PreferenceGroup) {                // TODO prio 3 - zkusit preferenceSummary jako rekurzivní algoritmus
                    PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                    for (int j=0; j<preferenceGroup.getPreferenceCount(); j++ ) {
                        Preference singlePref = preferenceGroup.getPreference(j);
                        updatePreferenceSummary(singlePref, singlePref.getKey());
                    }
                }
                updatePreferenceSummary(preference, preference.getKey());
            }
        }

        private void updatePreferenceSummary(Preference preference, String key) {
            // key zde není použito, ale dalo by se využít, kdybych nastavoval summary na základě klíče pro jednotlivé položky
            if (preference == null) return;

            if (preference instanceof ListPreference) {
                preference.setSummary(((ListPreference) preference).getEntry());

            }
            if (preference instanceof EditTextPreference) {
                preference.setSummary(((EditTextPreference) preference).getText());
            }
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(android.R.id.content, new FrgPreferences())
                .commit ();

    }
}

// Komentář k OnSharedPreferenceChanged
//    /**
//     * Called when a shared preference is changed, added, or removed. This
//     * may be called even if a preference is set to its existing value.
//     * <p>
//     * <p>This callback will be run on your main thread.
//     *
//     * @param sharedPreferences The {@link SharedPreferences} that received
//     *                          the change.
//     * @param key               The key of the preference that was changed, added, or
//     */

//
//    /**
//     * Subclasses should override this method and verify that the given fragment is a valid type
//     * to be attached to this activity. The default implementation returns <code>true</code> for
//     * apps built for <code>android:targetSdkVersion</code> older than
//     * {@link Build.VERSION_CODES#KITKAT}. For later versions, it will throw an exception.
//     *
//     * @param fragmentName the class name of the Fragment about to be attached to this activity.
//     * @return true if the fragment class name is valid for this Activity and false otherwise.
//     */

//    @Override
//    protected boolean isValidFragment(String fragmentName) {
//        return FrgPreferences.class.getName().equals(fragmentName);
//    }
