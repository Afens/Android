package afens.pr2t.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import afens.pr2t.R;

/**
 * Created by Afens on 01/03/2016.
 */
public class FragmentPreferencias extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        actualizarSummary(findPreference(key));
    }

    private void actualizarSummary(Preference preference) {
        if(preference instanceof ListPreference){
            ListPreference pref = (ListPreference) preference;
            pref.setSummary(pref.getEntry());
        }
    }
    @Override
    public void onResume() {// Se registra la actividad como listener de los cambios en las
        // preferencias.
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        // Se elimina la actividad como listener de los cambios en las
        // preferencias.
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }


}