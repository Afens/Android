package afens.pr015parcelable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final int RC_OTRA = 1;
    private static final String STATE_ALUMNO = "State alumno";
    private Alumno mAlum;
    private EditText txtDni;
    private TextView lblNombre;
    private TextView lblEdad;
    private Button btnObtener;
    private TextView lblGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (savedInstanceState!=null) {
            mAlum=savedInstanceState.getParcelable(STATE_ALUMNO);
            alumnoToVistas();
        }
        else {
            mAlum = new Alumno();
        }


    }

    private void initView() {
        txtDni = (EditText) findViewById(R.id.txtDni);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblEdad = (TextView) findViewById(R.id.lblEdad);
        btnObtener = (Button) findViewById(R.id.btnObtener);
        lblGenero= (TextView) findViewById(R.id.lblGenero);
        btnObtener.setEnabled(false);
        btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtener();
            }
        });
        txtDni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                btnObtener.setEnabled(!TextUtils.isEmpty(txtDni.getText().toString()));

            }
        });
    }

    private void obtener() {
        vistaToAlumno();
        OtraActivity.start(this, mAlum, RC_OTRA);
    }

    private void vistaToAlumno() {
        mAlum.setDni(txtDni.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_OTRA) {
            if (data.hasExtra(OtraActivity.EXTRA_ALUM)) {
                mAlum =data.getParcelableExtra(OtraActivity.EXTRA_ALUM);
                alumnoToVistas();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_ALUMNO, mAlum);
        super.onSaveInstanceState(outState);

    }

    private void alumnoToVistas() {
        lblNombre.setText(mAlum.getNombre());
        lblEdad.setText(mAlum.getEdad());
        lblGenero.setText(mAlum.getSexo());
    }


}
