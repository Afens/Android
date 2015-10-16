package afens.pr014intentresult;

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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final int RC_OTRA = 1;
    private EditText txtDni;
    private TextView lblNombre;
    private TextView lblEdad;
    private Button btnObtener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        txtDni = (EditText) findViewById(R.id.txtDni);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblEdad = (TextView) findViewById(R.id.lblEdad);
        btnObtener = (Button) findViewById(R.id.btnObtener);
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
        OtraActivity.start(this, txtDni.getText().toString(), RC_OTRA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_OTRA) {
            if (data.hasExtra(OtraActivity.EXTRA_EDAD) && data.hasExtra(OtraActivity.EXTRA_NOMBRE)) {
                lblEdad.setText(data.getStringExtra(OtraActivity.EXTRA_EDAD));
                lblNombre.setText(data.getStringExtra(OtraActivity.EXTRA_NOMBRE));
            }
        }
    }
}
