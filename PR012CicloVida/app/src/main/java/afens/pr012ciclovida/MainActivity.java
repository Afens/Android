package afens.pr012ciclovida;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_CONTADOR = "Estado";
    private int mCont=0;
    private TextView lblContador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState!=null){
            mCont=savedInstanceState.getInt(STATE_CONTADOR);
        }
        initView();
    }

    private void initView() {
        lblContador= (TextView) findViewById(R.id.lblContador);
        lblContador.setText(mCont + "");
        ((Button) findViewById(R.id.btnIncrement)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCont++;
                lblContador.setText(mCont + "");
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CONTADOR,mCont);
    }


}
