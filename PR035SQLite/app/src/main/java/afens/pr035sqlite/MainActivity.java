package afens.pr035sqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlumnoAdapter.OnItemClickListener {

    private AlumnoAdapter mAlumnoAdapter;
    private ProgressBar prbCirculo;
    private RecyclerView rvAlumnos;
    private List<Alumno> alumnos;

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

        if (savedInstanceState == null)
            cargarAlumnos();


        configurarRecyclerView();
    }

    private void cargarAlumnos() {
        alumnos = DAO.getInstance(getApplication()).getAllAlumnos();
    }


    private void configurarRecyclerView() {
        prbCirculo = (ProgressBar) findViewById(R.id.prbCirculo);
        rvAlumnos = (RecyclerView) findViewById(R.id.rvAlumnos);
        
        mAlumnoAdapter = new AlumnoAdapter((ArrayList<Alumno>)alumnos);
        mAlumnoAdapter.setOnItemClickListener(this);
        mAlumnoAdapter.setEmptyView(prbCirculo);
        rvAlumnos.setAdapter(mAlumnoAdapter);
        rvAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAlumnos.setItemAnimator(new DefaultItemAnimator());
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

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {

    }
}
