package afens.pr010percentrelativelayout;

import android.support.design.widget.Snackbar;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView lbl1;
    private PercentRelativeLayout raiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        lbl1=(TextView) findViewById(R.id.lbl1);
        lbl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lbl1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                snackbar(getString(R.string.msgSnack));

            }
        });
raiz=(PercentRelativeLayout) findViewById(R.id.raiz);
    }

    private void snackbar(String mensaje) {
        Snackbar.make(raiz,mensaje,Snackbar.LENGTH_SHORT).setAction(R.string.deshacer, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lbl1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.cian));
            }
        }).show();
    }

}
