package com.JKSoft.TdbClient;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

/**
 * Created by Jirka on 27.9.2016.
 */
public class actPreferences extends PreferenceActivity {
    /**
     * Subclasses should override this method and verify that the given fragment is a valid type
     * to be attached to this activity. The default implementation returns <code>true</code> for
     * apps built for <code>android:targetSdkVersion</code> older than
     * {@link Build.VERSION_CODES#KITKAT}. For later versions, it will throw an exception.
     *
     * @param fragmentName the class name of the Fragment about to be attached to this activity.
     * @return true if the fragment class name is valid for this Activity and false otherwise.
     */
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return FrgPreferences.class.getName().equals(fragmentName);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(android.R.id.content, new FrgPreferences())
                .commit ();

    }

}
