package afens.pr2t.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import afens.pr2t.R;
import afens.pr2t.fragments.FragmentPreferencias;

/**
 * Created by Afens on 01/03/2016.
 */
public class PreferenceActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        FragmentPreferencias frgPreference = new FragmentPreferencias();

        getFragmentManager().beginTransaction().replace(R.id.frmContenido, frgPreference).commit();
    }

    @Override
    public void finish() {
        Intent resultado = new Intent();
        setResult(RESULT_OK, resultado);
        super.finish();
    }
}
