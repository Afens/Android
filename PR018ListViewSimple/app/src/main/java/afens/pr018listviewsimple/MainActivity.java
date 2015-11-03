package afens.pr018listviewsimple;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_LISTA = "estadoLista";
    private static final String STATE_DATOS = "datosLista";
    private EditText txtNombre;
    private ImageButton btnAgregar;
    private ListView lstAlumnos;
    private Parcelable mEstadoLista;
    private ArrayList<String> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState==null)
            alumnos=new ArrayList<String>();
        else{
            mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
            alumnos = savedInstanceState.getStringArrayList(STATE_DATOS);
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
                if (!TextUtils.isEmpty(nombre)) {
                    agregarAlumno(nombre);
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
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alumnos);
        lstAlumnos.setAdapter(adaptador);
        lstAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View view, int position, long l) {
                String nombre = (String) lst.getItemAtPosition(position);
                enviar(nombre);
            }
        });
        lstAlumnos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                eliminarAlumno(adapterView.getItemAtPosition(i));
                return true;
            }
        });
    }

    private void enviar(String nombre) {
        OtraActivity.start(this, nombre);
    }

    private void checkDatos(String nombre) {
        btnAgregar.setEnabled(!TextUtils.isEmpty(nombre));
    }

    private void agregarAlumno(String nombre) {
        ((ArrayAdapter) lstAlumnos.getAdapter()).add(nombre);
        txtNombre.setText("");
        checkDatos(txtNombre.getText().toString());
    }

    private void eliminarAlumno(Object itemAtPosition) {
        ((ArrayAdapter) lstAlumnos.getAdapter()).remove((String) itemAtPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del ListView.
        mEstadoLista = lstAlumnos.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putStringArrayList(STATE_DATOS, alumnos);
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
