package afens.pr011pluralscadarg;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnVer;
    private TextInputLayout tilSus;
    private EditText txtSus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        btnVer=(Button) findViewById(R.id.btnVer);
        tilSus=(TextInputLayout) findViewById(R.id.tilSus);
        txtSus=(EditText) findViewById(R.id.txtSus);

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cant=Integer.valueOf(txtSus.getText().toString());
                Toast.makeText(getApplicationContext(),
                        getResources().getQuantityString(R.plurals.numAlum,
                                cant,cant,cant)
                        ,Toast.LENGTH_LONG
                        ).show();
            }
        });
    }


}
