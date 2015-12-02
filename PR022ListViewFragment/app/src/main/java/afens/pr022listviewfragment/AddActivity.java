package afens.pr022listviewfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {


    public static final String EXTRA_CONTACTO = "Nuevo_contacto";
    @Bind(R.id.txtNombre)
    EditText txtNombre;
    @Bind(R.id.tilNombre)
    TextInputLayout tilNombre;
    @Bind(R.id.txtEdad)
    EditText txtEdad;
    @Bind(R.id.tilEdad)
    TextInputLayout tilEdad;
    @Bind(R.id.ivFoto)
    ImageView ivFoto;
    @Bind(R.id.txtTelf)
    EditText txtTelf;
    @Bind(R.id.tilTelf)
    TextInputLayout tilTelf;
    @Bind(R.id.txtCorreo)
    EditText txtCorreo;
    @Bind(R.id.tilCorreo)
    TextInputLayout tilCorreo;
    @Bind(R.id.txtLocalidad)
    EditText txtLocalidad;
    @Bind(R.id.tilLocalidad)
    TextInputLayout tilLocalidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        setupValidacionTelefono();
        setupValidacionEmail();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflador = getMenuInflater();
        inflador.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnuConfirm){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Configura la validación del teléfono.
    private void setupValidacionTelefono() {
        txtTelf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtTelf.getText().toString())) {
                    if (!isValidPhoneNumber(txtTelf.getText().toString())) {
                        tilTelf.setError("No es un número de teléfono válido");
                    } else {
                        tilTelf.setErrorEnabled(false);
                    }
                } else {
                    tilTelf.setErrorEnabled(false);
                }
            }
        });
    }

    // Configura la validación del email.
        private void setupValidacionEmail () {
            txtCorreo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!TextUtils.isEmpty(txtCorreo.getText().toString())) {
                        if (!Patterns.EMAIL_ADDRESS.matcher(txtCorreo.getText().toString()).matches()) {
                            tilCorreo.setError("Debe tener el formato usuario@dominio.tipo");
                        } else {
                            tilCorreo.setErrorEnabled(false);
                        }
                    } else {
                        tilCorreo.setErrorEnabled(false);
                }
            }
        });
    }

    // Retorna si la cadena recibida es un número de teléfono válido.
    private boolean isValidPhoneNumber(String cadena) {
        if (cadena.length() > 0 && cadena.length() < 9) return false;
        if (!cadena.startsWith("6") && !cadena.startsWith("7") &&
                !cadena.startsWith("8") && !cadena.startsWith("9"))
            return false;
        return true;
    }
    public static void startForResult(Activity activity, int rc) {
        Intent intent=new Intent(activity,AddActivity.class);
        activity.startActivityForResult(intent,rc);
    }
    public void finish() {
        Intent resultado = new Intent();
        Contacto contacto=crearContacto();
        resultado.putExtra(EXTRA_CONTACTO, contacto);
        setResult(RESULT_OK, resultado);
        super.finish();
    }

    private Contacto crearContacto() {
        Contacto contacto=new Contacto();
        contacto.setNombre(txtNombre.getText().toString());
        contacto.setEdad(Integer.parseInt(txtEdad.getText().toString()));
        contacto.setTelf(txtTelf.getText().toString());
        contacto.setCorreo(txtCorreo.getText().toString());
        contacto.setLocalidad(txtLocalidad.getText().toString());
        contacto.setFoto("http://lorempixel.com/image_output/people-q-c-500-500-9.jpg");
        return contacto;
    }
}
