package afens.pr022listviewfragment;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UnoFragment.Callback, DosFragment.Edit {


    private static final int RC_ADD = 1;
    private static final int RC_DETAIL = 2;
    private static final int RC_EDIT = 3;
    private static final String PRINCIPAL = "Principal";
    private static final String SEGUNDARIO = "Segundario";

    private FragmentManager gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestor = getSupportFragmentManager();
        // Si no se encuentra el hueco 2 intentamos eliminar
        // el fragmento segundario de la pila
        if (findViewById(R.id.flHueco2) == null)
            eliminarFragmento();

        iniciarFragmento();
    }

    private void eliminarFragmento() {
        Fragment s = gestor.findFragmentByTag(SEGUNDARIO);
        if (s != null) {
            FragmentTransaction transaction = gestor.beginTransaction();
            transaction.remove(s);
            transaction.commit();
        }
    }

    private void iniciarFragmento() {
        FragmentTransaction transaction = gestor.beginTransaction();
        transaction.replace(R.id.flHueco, new UnoFragment(), PRINCIPAL);
        transaction.commit();
    }

    // ------------------------ Interfaces de Fragmentos -------------------------------
    @Override
    //Ver los detalles del contacto selecionado
    public void verDetalles(int contacto) {
        if (findViewById(R.id.flHueco2) == null) {
            SecundariaActivity.start(MainActivity.this, contacto, RC_DETAIL);
        } else {
            FragmentTransaction transaction = gestor.beginTransaction();
            transaction.replace(R.id.flHueco2, DosFragment.newInstance(contacto), SEGUNDARIO);
            transaction.commit();
        }
    }

    @Override
    // Solicitar a otra actividad un contacto nuevo
    public void solicitarContacto() {
        AddActivity.startForResult(this, RC_ADD, -1);
    }
    @Override
    public void editarContacto(int contacto) {
        AddActivity.startForResult(this, RC_EDIT, contacto);
    }
    // -------------------------------------------------------------------------------


    @Override
    // Cuando nos responde un Intent
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_ADD && data.hasExtra(AddActivity.EXTRA_CONTACTO)) {
                Contacto c = data.getParcelableExtra(AddActivity.EXTRA_CONTACTO);
                Toast.makeText(this, "Contacto Añadido", Toast.LENGTH_SHORT).show();
                UnoFragment frg = (UnoFragment) getSupportFragmentManager().findFragmentByTag(PRINCIPAL);
                frg.addContacto(c);
            }
            if (requestCode == RC_EDIT && data.hasExtra(AddActivity.EXTRA_CONTACTO)) {
                if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Fragment frg = getSupportFragmentManager().findFragmentByTag(PRINCIPAL);
                    ((UnoFragment)frg).actualizar();
                    frg = getSupportFragmentManager().findFragmentByTag(SEGUNDARIO);
                    ((DosFragment)frg).mostrarDetalles();
                }
            }
            if (requestCode == RC_DETAIL && data.hasExtra(SecundariaActivity.EXTRA_MODIFICADO)) {
                if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    int i = data.getIntExtra(SecundariaActivity.EXTRA_MODIFICADO, -1);
                    UnoFragment frg = (UnoFragment) getSupportFragmentManager().findFragmentByTag(PRINCIPAL);
                    frg.lstContactos.setItemChecked(i, true);
                }
            }
        }
    }


/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELECIONADO, itemSeleccionado);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            itemSeleccionado = savedInstanceState.getParcelable(SELECIONADO);
            if (itemSeleccionado != null)
                verDetalles(itemSeleccionado);
        }
    }
    */
}
