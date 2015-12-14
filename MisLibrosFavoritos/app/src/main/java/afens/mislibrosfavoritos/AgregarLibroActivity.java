package afens.mislibrosfavoritos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AgregarLibroActivity extends AppCompatActivity {

    @Bind(R.id.imgPortada)
    ImageView imgPortada;
    @Bind(R.id.txtTitulo)
    EditText txtTitulo;
    @Bind(R.id.txtAutor)
    EditText txtAutor;
    @Bind(R.id.txtYear)
    EditText txtYear;
    @Bind(R.id.txtURL)
    EditText txtURL;
    @Bind(R.id.txtSinopsis)
    EditText txtSinopsis;
    @Bind(R.id.add_toolbar)
    Toolbar addToolbar;
    @Bind(R.id.tilTitulo)
    TextInputLayout tilTitulo;


    public static void startForResult(Activity activity, int rc) {
        Intent intent = new Intent(activity, AgregarLibroActivity.class);
        activity.startActivityForResult(intent, rc);

    }

    public void finish() {
        Intent resultado = new Intent();
        setResult(RESULT_OK, resultado);
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_libro);
        ButterKnife.bind(this);
        setSupportActionBar(addToolbar);

        cargarPortada();
        txtURL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                cargarPortada();
            }
        });
        txtTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtTitulo.getText())) {
                    if (txtTitulo.getText().toString().trim().length() > 0) {
                        tilTitulo.setError("");
                        tilTitulo.setErrorEnabled(false);

                    } else
                        tilTitulo.setError("El Titulo no puede ser una cadena Vacia");
                } else
                    tilTitulo.setError("Tiene que tener un Titulo");
            }
        });
    }

    private void cargarPortada() {
        if (txtURL.getText().toString().trim().length()>0) {
            Picasso.with(getApplicationContext()).load(txtURL.getText().toString()).error(R.drawable.portada).into(imgPortada);
        } else
            Picasso.with(this).load(R.drawable.portada).into(imgPortada);
    }
    private void guardarlibro() {
        Libro libro = new Libro();
        libro.setTitulo(txtTitulo.getText().toString().trim());
        if (!TextUtils.isEmpty(txtYear.getText().toString().trim()))
            libro.setYear_publicacion(Integer.parseInt(txtYear.getText().toString()));
        libro.setUrl_portada(txtURL.getText().toString().trim());
        libro.setSinopsis(txtSinopsis.getText().toString().trim());
        libro.setAutor(txtAutor.getText().toString().trim());
        Coleccion.add(libro);
    }
// --------------------- Menu -----------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflador = getMenuInflater();
        inflador.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuGuardar:
                if (!TextUtils.isEmpty(txtTitulo.getText())) {
                    guardarlibro();
                    Toast.makeText(this, "libro Añadido", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    tilTitulo.setError("Tiene que tener un Titulo");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
