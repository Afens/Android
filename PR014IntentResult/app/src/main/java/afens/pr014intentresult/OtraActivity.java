package afens.pr014intentresult;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class OtraActivity extends AppCompatActivity {

    public static final String EXTRA_DNI = "DNI";
    public static final String EXTRA_NOMBRE = "NOMBRE";
    public static final String EXTRA_EDAD = "EDAD";
    private TextView lblDni;
    private EditText txtEdad;
    private EditText txtNombre;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
        initView();
        Intent intent = getIntent();
        lblDni.setText(intent.getStringExtra(EXTRA_DNI));
    }

    private void initView() {
        lblDni = (TextView) findViewById(R.id.lblDni);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
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
        resultado.putExtra(EXTRA_NOMBRE, txtNombre.getText().toString());
        resultado.putExtra(EXTRA_EDAD, txtEdad.getText().toString());
        setResult(RESULT_OK, resultado);
        super.finish();
    }

    public static void start(Activity activity, String dni, int rc) {
        Intent intent = new Intent(activity, OtraActivity.class);
        intent.putExtra(EXTRA_DNI, dni);
        activity.startActivityForResult(intent, rc);
    }
}
