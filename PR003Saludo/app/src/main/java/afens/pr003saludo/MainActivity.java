package afens.pr003saludo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre;
    private CheckBox chkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        chkBox = (CheckBox) findViewById(R.id.chkBox);
        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked())
                    Toast.makeText(getApplicationContext(), R.string.eduOn, Toast.LENGTH_LONG);
                else
                    Toast.makeText(getApplicationContext(), R.string.eduOff, Toast.LENGTH_LONG);
            }
        });
    }

    public void clic(View v) {
        if (chkBox.isChecked()) {
            Toast.makeText(getApplicationContext(), getString(R.string.hola) + " " + txtNombre.getText().toString(), Toast.LENGTH_LONG);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.saludoEducado) + " " + txtNombre.getText().toString(), Toast.LENGTH_LONG);
        }
    }


}
