package afens.pr009textimputlayout;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtPhone;
    private EditText txtEmail;
    private TextInputLayout tilPhone;
    private TextInputLayout tilEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        txtName = (EditText) findViewById(R.id.txtName);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        tilPhone = (TextInputLayout) findViewById(R.id.tilPhone);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);

        txtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!checkPhone())
                    tilPhone.setError("No es un numero de telefono valido");
                else
                    tilPhone.setErrorEnabled(false);
            }
        });

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtEmail.getText().toString())) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
                        tilEmail.setError("Debe tener el formato usuario@dominio.tipo");
                    } else {
                        tilEmail.setErrorEnabled(false);
                    }
                } else {
                    tilEmail.setErrorEnabled(false);
                }
            }
        });
    }

    private boolean checkPhone() {
        if(txtPhone.length()!=9)
            return false;
        String cadena=txtPhone.getText().toString();
        if(!cadena.startsWith("6") && !cadena.startsWith("7") && !cadena.startsWith("8") && !cadena.startsWith("9"))
            return false;
        return true;

    }


}
