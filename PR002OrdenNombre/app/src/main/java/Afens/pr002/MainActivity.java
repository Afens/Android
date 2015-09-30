package Afens.pr002;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView lblMensaje;
    private EditText txtApellido;
    private EditText txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniView();
    }

    private void iniView() {
        lblMensaje=(TextView)findViewById(R.id.lblMensaje);
        txtNombre=(EditText)findViewById(R.id.txtNombre);
        txtApellido=(EditText)findViewById(R.id.txtApellido);
    }

    public void option1 (View v){
        lblMensaje.setText(getString(R.string.Sr) + " " + txtApellido.getText().toString() + "," + txtNombre.getText().toString());
    }
    public void option2 (View v){
        lblMensaje.setText(getString(R.string.Sr) + " " + txtNombre.getText().toString() + txtApellido.getText().toString());
    }
}
