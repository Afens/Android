package afens.pr033preferencias;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferenciasFragment())
                .commit();
        /*onResume
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        String lema = preferencias.getString("prefTitle", "");
        setTitle(lema);
        */
    }
}
