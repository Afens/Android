package afens.pr004estaciones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imgEst;
    private RadioGroup rdg;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        imgEst = (ImageView) findViewById(R.id.imgEst);
        rdg = (RadioGroup) findViewById(R.id.rdg);
        btn = (Button) findViewById(R.id.btn);

        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rdbPri:
                        imgEst.setImageResource(R.drawable.pri);
                        break;
                    case R.id.rdbVer:
                        imgEst.setImageResource(R.drawable.ver);
                        break;
                    case R.id.rdbOto:
                        imgEst.setImageResource(R.drawable.oto);
                        break;
                    case R.id.rdbInv:
                        imgEst.setImageResource(R.drawable.inv);
                        break;
                }

            }
        });
        rdg.check(R.id.rdbPri);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje=getString(R.string.megusta)+" ";
                switch (rdg.getCheckedRadioButtonId()){
                    case R.id.rdbPri:
                        mensaje+= getString(R.string.primavera);
                        break;
                    case R.id.rdbVer:
                        mensaje+= getString(R.string.verano);
                        break;
                    case R.id.rdbOto:
                        mensaje+= getString(R.string.otonno);
                        break;
                    case R.id.rdbInv:
                        mensaje+= getString(R.string.invierno);
                        break;
                }

                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
            }
        });

    }


}
