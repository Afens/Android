package afens.pr2t.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;


import afens.pr2t.App;
import afens.pr2t.R;
import afens.pr2t.fragments.InfoDialogFragment;
import afens.pr2t.fragments.FragmentEditar;
import afens.pr2t.fragments.FragmentLista;
import afens.pr2t.fragments.FragmentTutorias;
import afens.pr2t.fragments.FragmentVisitas;
import afens.pr2t.modelos.Alumno;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentLista.FragmentListaListener, View.OnClickListener {


    private static final int RC_PREFERENCES = 5;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private FragmentManager mGestorFragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configToolbar();
        configDrawerLayout();
        configNavigationView();
        configFab();
        configFragments();

    }

    private void configFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setImageResource(R.drawable.ic_add);
    }

    private void configFragments() {
        mGestorFragmento = getSupportFragmentManager();
        if (mGestorFragmento.findFragmentById(R.id.frmHueco) == null) {
            mGestorFragmento.beginTransaction().replace(R.id.frmHueco, cargarFragmentoInicial()).commit();
        }
    }

    private Fragment cargarFragmentoInicial() {
        Fragment fragment;
        String prefEscogida = PreferenceManager.getDefaultSharedPreferences(this).getString(App.PREF_FRAGMENTO_INICIAL, getString(R.string.itemTutorias));
        switch (prefEscogida) {
            case App.FRG_INI_PROX_VISITAS:
                fragment = new FragmentVisitas();
                fab.setVisibility(View.INVISIBLE);
                break;
            default:
                fragment = new FragmentLista();
                break;
        }
        return fragment;

    }

    private void configToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void configDrawerLayout() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void configNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = cargarFragmentoInicial();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mGestorFragmento.findFragmentById(R.id.frmHueco) instanceof FragmentLista && fragment instanceof FragmentLista) {
            super.onBackPressed();
        } else if (mGestorFragmento.findFragmentById(R.id.frmHueco) instanceof FragmentVisitas && fragment instanceof FragmentVisitas) {
            super.onBackPressed();
        } else {
            mGestorFragmento.beginTransaction().replace(R.id.frmHueco, fragment).commit();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction transaction = mGestorFragmento.beginTransaction();
        int id = item.getItemId();
        Fragment fragment = null;
        fab.setVisibility(View.VISIBLE);
        switch (id) {
            case R.id.nav_new_Alumno:
                fragment = new FragmentEditar();
                break;
            case R.id.nav_tutorias:
                fragment = new FragmentLista();
                break;
            case R.id.nav_proximas_visitas:
                fragment = new FragmentVisitas();
                fab.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_config:
                startActivityForResult(new Intent(this, PreferenceActivity.class), RC_PREFERENCES);
                break;
            case R.id.nav_info:
                InfoDialogFragment frgMiDialogo = new InfoDialogFragment();
                frgMiDialogo.show(getSupportFragmentManager(), "Info");
                break;

        }
        if (fragment != null)
            transaction.replace(R.id.frmHueco, fragment).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Click del fragmento de lista de alumnos.
    @Override
    public void onAlumnoSelected(Alumno alumno) {
        Fragment frgActual = FragmentTutorias.newInstance(alumno);
        FragmentTransaction transaction = mGestorFragmento.beginTransaction();
        transaction.replace(R.id.frmHueco, frgActual).commit();
    }

    @Override
    public void addAlumno() {
        FragmentTransaction transaction = mGestorFragmento.beginTransaction();
        FragmentEditar fragment = new FragmentEditar();
        transaction.replace(R.id.frmHueco, fragment).commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = mGestorFragmento.findFragmentById(R.id.frmHueco);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FragmentEditar.RC_SELECCIONAR_FOTO:
                    //Comprobamos si nos encontramos en el fragmento Tutorias o en el Editar directamente
                    if (fragment instanceof FragmentTutorias)
                        ((FragmentTutorias) fragment).getItem(((FragmentTutorias) fragment).getCurrentPage()).onActivityResult(requestCode, resultCode, data);
                    else
                        fragment.onActivityResult(requestCode, resultCode, data);
                    break;
                case FragmentVisitas.RC_VISITAS:
                    //Si estamos en el fragmento de tutorias
                    if (fragment instanceof FragmentTutorias) {
                        ((FragmentVisitas) ((FragmentTutorias) fragment).getItem(1)).cargarVisitas();
                    }else if (fragment instanceof FragmentVisitas)
                        ((FragmentVisitas)fragment).cargarVisitas();

                    break;
                case RC_PREFERENCES:
                    if (fragment instanceof FragmentLista || fragment instanceof FragmentVisitas){
                        mGestorFragmento.beginTransaction().replace(R.id.frmHueco, cargarFragmentoInicial()).commit();
                    }
            }
        }
    }


    // ------------------- Acciones del Fab -----------------
    @Override
    public void onClick(View v) {
        Fragment frg = mGestorFragmento.findFragmentById(R.id.frmHueco);

        //Si estamos en el fragmento de tutorias
        if (frg instanceof FragmentTutorias) {
            int currentPage = ((FragmentTutorias) frg).getCurrentPage();

            //Si estamos en el tab de el alumno solicitamos foto
            if (currentPage == 0)
                ((FragmentEditar) ((FragmentTutorias) frg).getItem(currentPage)).solicitarFoto();

                //Si estamos en el tab de visitas abrimos la actividad para crear una nueva visita
            else if (currentPage == 1) {
                Intent intent = new Intent(MainActivity.this, CrearVisitaActivity.class);
                intent.putExtra(CrearVisitaActivity.INTENT_ID_ALUMNO, ((FragmentTutorias) frg).getIdAlumno());
                startActivityForResult(intent, FragmentVisitas.RC_VISITAS);
            }

            //Si estamos en la lista abrimos el fragmento para crear un nuevo alumno
        } else if (frg instanceof FragmentLista) {
            Fragment newFragment = new FragmentEditar();
            FragmentTransaction trans = mGestorFragmento.beginTransaction();
            trans.replace(R.id.frmHueco, newFragment).commit();

            //Si estamos en fragmento editar solicitamos foto
        } else if (frg instanceof FragmentEditar) {
            ((FragmentEditar) frg).solicitarFoto();
        }
    }
}
