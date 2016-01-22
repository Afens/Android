package afens.pr033preferencias;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Usuario on 22/01/2016.
 */
public class PreferenciasFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
