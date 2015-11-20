package afens.pr018listviewsimple;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_LISTA = "estadoLista";
    private static final String STATE_DATOS = "datosLista";
    private EditText txtNombre;
    private ImageButton btnAgregar;
    private ListView lstAlumnos;
    private Parcelable mEstadoLista;
    private ArrayList<Alumno> alumnos;
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            alumnos = new ArrayList<>();
        else {
            mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
            alumnos = (ArrayList<Alumno>) savedInstanceState.getSerializable(STATE_DATOS);
        }
        initView();
    }

    private void initView() {
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString();
                int edad = Integer.valueOf(((EditText) findViewById(R.id.txtEdad)).getText().toString());
                if (!TextUtils.isEmpty(nombre)) {
                    agregarAlumno(nombre, edad);
                }
            }
        });
        txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkDatos(editable.toString());
            }
        });

        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setEmptyView(findViewById(R.id.lblNoHayAlumnos));
        lstAlumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        adaptador = new Adaptador(this, alumnos);
        lstAlumnos.setAdapter(adaptador);
        lstAlumnos.setMultiChoiceModeListener(
                new AbsListView.MultiChoiceModeListener() {
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int i, long l, boolean b) {
                        // Se actualiza el título de la action bar contextual.
                        mode.setTitle(lstAlumnos.getCheckedItemCount()
                                + "de"
                                + lstAlumnos.getCount());
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.alumnos_menu, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mnuAprobar:
                                String mensaje = "";
                                // Se obtienen los elementos seleccionados.
                                ArrayList<Alumno> elementos = getElementosSeleccionados(
                                        lstAlumnos, false);
                                // Se añade cada elemento al mensaje
                                for (Alumno elemento : elementos) {
                                    mensaje = mensaje + elemento.nombre + " ";
                                }
                                // Se muestra el mensaje.
                                mensaje = getString(R.string.aprob) + mensaje;
                                Toast.makeText(getApplicationContext(), mensaje,
                                        Toast.LENGTH_LONG).show();
                                break;
                            case R.id.mnuEliminar:
                                // Se obtienen los elementos seleccionados (y se
                                // quita la selección).
                                ArrayList<Alumno> elems = getElementosSeleccionados(
                                        lstAlumnos, true);
                                // Se eliminan del adaptador.
                                for (Alumno elemento : elems) {
                                    adaptador.remove(elemento);
                                }
                                // Se notifica al adaptador que ha habido cambios.
                                adaptador.notifyDataSetChanged();
                                Toast.makeText(
                                        getApplicationContext(),
                                        elems.size()
                                                + getString(R.string.supr),
                                        Toast.LENGTH_LONG).show();
                                break;

                        }
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {

                    }
                }

        );
        lstAlumnos.setOnItemClickListener(
                new AdapterView.OnItemClickListener()

                {
                    @Override
                    public void onItemClick(AdapterView<?> lst, View view, int position, long l) {
                        Alumno nombre = (Alumno) lst.getItemAtPosition(position);
                        enviar(nombre.nombre);
                    }
                }

        );

    }

    private ArrayList<Alumno> getElementosSeleccionados(ListView lst, boolean uncheck) {
        // ArrayList resultado.
        ArrayList<Alumno> datos = new ArrayList<>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lst.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                int position = selec.keyAt(i);
                // Se quita de la selección (si hay que hacerlo).
                if (uncheck) {
                    lst.setItemChecked(position, false);
                }
                // Se añade al resultado.
                datos.add((Alumno) lst.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

    private void enviar(String nombre) {
        OtraActivity.start(this, nombre);
    }

    private void checkDatos(String nombre) {
        btnAgregar.setEnabled(!TextUtils.isEmpty(nombre));
    }

    private void agregarAlumno(String nombre, int edad) {
        ((ArrayAdapter) lstAlumnos.getAdapter()).add(new Alumno(nombre, edad));
        txtNombre.setText("");
        checkDatos(txtNombre.getText().toString());
    }

    private void eliminarAlumno(Object itemAtPosition) {
        ((ArrayAdapter) lstAlumnos.getAdapter()).remove((Alumno) itemAtPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del ListView.
        mEstadoLista = lstAlumnos.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putSerializable(STATE_DATOS, alumnos);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Se obtiene el estado anterior de la lista.
        //mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            lstAlumnos.onRestoreInstanceState(mEstadoLista);
        }
    }
}
