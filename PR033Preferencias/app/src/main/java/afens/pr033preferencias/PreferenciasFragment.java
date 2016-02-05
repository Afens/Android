package afens.pr033preferencias;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import java.util.Set;

/**
 * Created by Usuario on 22/01/2016.
 */
public class PreferenciasFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se construye el fragmento de preferencias a partir de la
        // especificación XML de preferencias.
        addPreferencesFromResource(R.xml.preferences);

        // Se inicializan los summary con el valor de la preferencia.
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            inicializarSummary(getPreferenceScreen().getPreference(i));
        }
    }

    // Inicializa el summary de una preferencia. Llama recursivamente si se
    // trata de una pantalla de preferencias o una categoría de preferencias.
    private void inicializarSummary(Preference preferencia) {
        // Si la preferencia corresponde a otra pantalla de preferencias, se
        // llama recursivamente.
        if (preferencia instanceof PreferenceScreen) {
            PreferenceScreen pantalla = (PreferenceScreen) preferencia;
            for (int i = 0; i < pantalla.getPreferenceCount(); i++) {
                inicializarSummary(pantalla.getPreference(i));
            }
        }
        // Si la preferencia corresponde a una categoría de preferencias, se
        // llama recursivamente.
        else if (preferencia instanceof PreferenceCategory) {
            PreferenceCategory categoria = (PreferenceCategory) preferencia;
            for (int i = 0; i < categoria.getPreferenceCount(); i++) {
                inicializarSummary(categoria.getPreference(i));
            }
        } else {
            // Se actualiza el valor del summary a partir del valor de la
            // preferencia.
            actualizarSummary(preferencia);
        }

    }


    @Override
    public void onResume() {
        // Se registra la actividad como listener de los cambios en las
        // preferencias.
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
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

    // Cuando se cambia el valor de una preferencia.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        // Se busca esa preferencia en el árbol de preferencias y se actualiza
        // el summary a partir del valor que contenga.
        actualizarSummary(findPreference(key));
    }

    // Actuliza el summary de una preferencia dependiendo del tipo que sea.
    private void actualizarSummary(Preference preferencia) {
        // Si es un EditTextPreference.
        if (preferencia instanceof EditTextPreference) {
            EditTextPreference pref = (EditTextPreference) preferencia;
            // Se establece como summary el valor (textual) de la preferencia.
            pref.setSummary(pref.getText());


            if (pref.getKey().equals("prefTitle"))
                getActivity().setTitle(pref.getText());

        }
        // Si es un ListPreference.
        else if (preferencia instanceof ListPreference) {
            ListPreference pref = (ListPreference) preferencia;
            pref.setSummary(pref.getEntry());
        }
        // Si es un MultiSelectListPreference.
        else if (preferencia instanceof MultiSelectListPreference) {
            MultiSelectListPreference pref = (MultiSelectListPreference) preferencia;
            Set<String> seleccionados = pref.getValues();
            pref.setSummary(seleccionados.toString());
        }
    }
}
