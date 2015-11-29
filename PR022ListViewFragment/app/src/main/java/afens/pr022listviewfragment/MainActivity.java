package afens.pr022listviewfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UnoFragment.Callback {


    FragmentManager gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestor = getSupportFragmentManager();
        iniciarFragmento();
    }

    private void iniciarFragmento() {
        FragmentTransaction transaction = gestor.beginTransaction();
        transaction.replace(R.id.flHueco, new UnoFragment(), "Principal");
        transaction.commit();
    }


    @Override
    public void verDetalles(Contacto contacto) {
        if (findViewById(R.id.flHueco2) == null)
            SecundariaActivity.start(MainActivity.this, contacto);
        else {
            FragmentTransaction transaction = gestor.beginTransaction();
            transaction.replace(R.id.flHueco2, DosFragment.newInstance(contacto));
            transaction.commit();
        }
    }
}
