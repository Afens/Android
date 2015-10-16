package afens.pr013intents;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OtraActivity extends AppCompatActivity {

    public static final String EXTRA_TEXTO = "TEXTO";
    private TextView lblTexto;
    private String mTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
        Intent a=getIntent();
        mTexto=a.getStringExtra(EXTRA_TEXTO);
        initView();

    }

    private void initView() {
        lblTexto=(TextView) findViewById(R.id.lblTexto);
        lblTexto.setText(mTexto);


    }
    public static void start(Context context,String texto) {
        Intent intencion = new Intent(context, OtraActivity.class);
        intencion.putExtra(EXTRA_TEXTO, texto);
        context.startActivity(intencion);
    }
}
