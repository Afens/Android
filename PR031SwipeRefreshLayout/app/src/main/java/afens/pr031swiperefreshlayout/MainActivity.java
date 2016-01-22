package afens.pr031swiperefreshlayout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView lstLista;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swlPanel;
    private ArrayList<String> mDatos;
    private final SimpleDateFormat mFormateador = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private ListaAdapter mAdaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mDatos = new ArrayList<>();
        mDatos.add(mFormateador.format(new Date()));
        setupPanel();
        setupRecyclerView();
    }


    private void setupRecyclerView() {
        lstLista = (RecyclerView) findViewById(R.id.lstLista);
        lstLista.setHasFixedSize(true);
        mAdaptador = new ListaAdapter(mDatos);
        lstLista.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        lstLista.setLayoutManager(mLayoutManager);

        lstLista.setItemAnimator(new DefaultItemAnimator());
    }

    // Configura el SwipeRefreshLayout.
    private void setupPanel() {
        swlPanel = (SwipeRefreshLayout)
                findViewById(R.id.swlPanel);
        // El fragmento actuará como listener del gesto de swipe.
        swlPanel.setOnRefreshListener(this);
        // Se establecen los colores que debe usar la animación.
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
    @Override
    public void onRefresh() {
        refrescar();
    }

    private void refrescar() {
        // Se activa la animación.
        swlPanel.setRefreshing(true);
        // Se simula que la tarea de carga tarda unos segundos.


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
