package afens.pr006relativelayout;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView lblUser;
    private TextView lblPass;
    private EditText txtUser;
    private EditText txtPass;
    private Button btnAceptar;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        lblUser = (TextView) findViewById(R.id.lblUser);
        lblPass = (TextView) findViewById(R.id.lblPass);
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnAceptar.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        lblUser.setVisibility(View.INVISIBLE);
        lblPass.setVisibility(View.INVISIBLE);

        txtUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                foco(lblUser, hasFocus);
            }
        });
        txtPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                foco(lblPass,hasFocus);
            }
        });

        txtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                activar();
                visibilidad(txtUser, lblUser);
            }
        });
        txtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                activar();
                visibilidad(txtPass, lblPass);
            }
        });
    }

    //Activar y desactivar Aceptar cuando Usuario y Clave contienen datos
    private void activar() {
        btnAceptar.setEnabled(!TextUtils.isEmpty(txtUser.getText().toString())
                && !TextUtils.isEmpty(txtPass.getText().toString()));
    }

    //Mostrar Usuario y/o Clave cuando el EditText tiene datos
    private void visibilidad(EditText txt, TextView lbl) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            lbl.setVisibility(View.INVISIBLE);
        } else {
            lbl.setVisibility(View.VISIBLE);
        }
    }

    //Al hacer click en un boton
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAceptar:
                Toast.makeText(this, getString(R.string.conectW) + " "
                        + txtUser.getText().toString() + "...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancel:
                txtUser.setText("");
                txtPass.setText("");
                break;
        }
    }
    //Cambia el color del lbl dependiendo de quien tenga el foco
    public void foco(TextView lbl, boolean tieneFoco) {
        if (tieneFoco){
            lbl.setTextColor(getResources().getColor(R.color.accent));
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            lbl.setTextColor(getResources().getColor(R.color.primary_text));
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }
}
