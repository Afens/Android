package afens.mislibrosfavoritos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ListaFragment.Callback, SinopsisFragment.Cerrar{

    private static final String LISTA = "LISTA";
    private static final String SINOPSIS = "SINOPSIS";
    private static final int RC_ADD = 1;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.flLista)
    FrameLayout flLista;
    @Bind(R.id.flSinopsis)
    FrameLayout flSinopsis;
    private FragmentManager gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        gestor = getSupportFragmentManager();
        iniciarLista();
    }

    private void iniciarLista() {
        FragmentTransaction transaction = gestor.beginTransaction();
        transaction.replace(R.id.flLista, new ListaFragment(), LISTA);
        transaction.commit();
    }

    // --------------Interface -----------------------------
    @Override
    public void verSinopsis(String titulo, String sinopsis) {
        FragmentTransaction transaction = gestor.beginTransaction();
        transaction.replace(R.id.flSinopsis, SinopsisFragment.newInstance(titulo,sinopsis), SINOPSIS);
        transaction.commit();
    }

    @Override
    public void cerrar() {
        Fragment s = gestor.findFragmentByTag(SINOPSIS);
        FragmentTransaction transaction = gestor.beginTransaction();
        transaction.remove(s);
        transaction.commit();
    }

    // ------------------- Menu ---------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflador = getMenuInflater();
        inflador.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuNuevo:
                AgregarLibroActivity.startForResult(this, RC_ADD);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
    //------------ Respuesta de Actividades ------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_ADD) {
                ListaFragment frg = (ListaFragment) getSupportFragmentManager().findFragmentByTag(LISTA);
                frg.actualizar();
            }
        }

    }
}
