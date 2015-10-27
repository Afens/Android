package afens.pr015parcelable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class OtraActivity extends AppCompatActivity {

    public static final String EXTRA_ALUM = "ALUMNO";
    private Alumno mAlum;
    private TextView lblDni;
    private EditText txtEdad;
    private EditText txtNombre;
    private Button btnEnviar;
    private Spinner spnGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
        initView();
        Intent intent = getIntent();
        mAlum =intent.getParcelableExtra(EXTRA_ALUM);
        alumnoToVista();
    }

    private void alumnoToVista() {
        lblDni.setText(mAlum.getDni());
    }

    private void initView() {
        lblDni = (TextView) findViewById(R.id.lblDni);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        spnGenero = (Spinner) findViewById(R.id.spnGenero);
        btnEnviar.setEnabled(false);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

                btnEnviar.setEnabled(!TextUtils.isEmpty(txtNombre.getText().toString())
                        && !TextUtils.isEmpty(txtEdad.getText().toString()));

            }
        });
        txtEdad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnEnviar.setEnabled(!TextUtils.isEmpty(txtNombre.getText().toString())
                        && !TextUtils.isEmpty(txtEdad.getText().toString()));
            }
        });
    }

    public void finish() {
        Intent resultado = new Intent();
        vistaToAlumno();
        resultado.putExtra(EXTRA_ALUM, mAlum);
        setResult(RESULT_OK, resultado);
        super.finish();
    }

    private void vistaToAlumno() {
        mAlum.setNombre(txtNombre.getText().toString());
        mAlum.setEdad(txtEdad.getText().toString());
        mAlum.setSexo(getResources().getStringArray(R.array.generos)[spnGenero.getSelectedItemPosition()]);
    }

    public static void start(Activity activity, Alumno alum, int rc) {
        Intent intent = new Intent(activity, OtraActivity.class);
        intent.putExtra(EXTRA_ALUM, alum);
        activity.startActivityForResult(intent, rc);
    }
}
