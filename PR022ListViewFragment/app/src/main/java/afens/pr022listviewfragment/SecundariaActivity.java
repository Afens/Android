package afens.pr022listviewfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecundariaActivity extends AppCompatActivity implements DosFragment.Edit{

    private static final String EXTRA_CONTACTO = "Contacto";
    private static final int RC_DETAIL = 2;
    private static final String SEGUNDARIO = "Segundario";
    public static final String EXTRA_MODIFICADO = "Modificado";
    private FragmentManager gestor;


    // -------------------------- Codigo Principal ----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        gestor = getSupportFragmentManager();
        Intent intent= getIntent();
        asignarFragmento(intent.getIntExtra(EXTRA_CONTACTO, -1));
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            onBackPressed();
        }
    }

    private void asignarFragmento(int extra) {
        FragmentTransaction transaction= gestor.beginTransaction();
        transaction.replace(R.id.flHueco2, DosFragment.newInstance(extra), SEGUNDARIO);
        transaction.commit();
    }

    // ----------------- Interface -----------------------------------------
    @Override
    public void editarContacto(int contacto) {
        AddActivity.startForResult(this, RC_DETAIL, contacto);
    }

    // ----------------- Intent para llamar a la actividad ----------------------------
    public static void start(Activity activity, int contacto, int rc) {
        Intent intent=new Intent(activity,SecundariaActivity.class);
        intent.putExtra(EXTRA_CONTACTO, contacto);
        activity.startActivityForResult(intent, rc);
    }

    // ------------------------- Comunicacion con otras actividades ----------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_DETAIL && data.hasExtra(AddActivity.EXTRA_CONTACTO)) {
                DosFragment frg= (DosFragment) getSupportFragmentManager().findFragmentByTag(SEGUNDARIO);
                frg.mostrarDetalles();
            }
        }
    }


    public void finish() {
        Intent resultado = new Intent();
        DosFragment frg= (DosFragment) getSupportFragmentManager().findFragmentByTag(SEGUNDARIO);
        Contacto contacto =frg.getContacto();
        resultado.putExtra(EXTRA_MODIFICADO, ListaContactos.indexOf(contacto));
        setResult(RESULT_OK, resultado);
        super.finish();
    }


}
