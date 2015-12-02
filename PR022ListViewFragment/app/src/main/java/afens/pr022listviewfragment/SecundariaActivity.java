package afens.pr022listviewfragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecundariaActivity extends AppCompatActivity {

    private static final String EXTRA_CONTACTO = "Contacto";
    private FragmentManager gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        gestor = getSupportFragmentManager();
        Intent intent= getIntent();
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            onBackPressed();
        asignarFragmento(intent.<Contacto>getParcelableExtra(EXTRA_CONTACTO));
    }

    private void asignarFragmento(Contacto extra) {
        FragmentTransaction transaction= gestor.beginTransaction();
        transaction.replace(R.id.flHueco2, DosFragment.newInstance(extra));
        transaction.commit();
    }

    public static void start(Context context, Contacto contacto) {
        Intent intent=new Intent(context,SecundariaActivity.class);
        intent.putExtra(EXTRA_CONTACTO, contacto);
        context.startActivity(intent);
    }
}
