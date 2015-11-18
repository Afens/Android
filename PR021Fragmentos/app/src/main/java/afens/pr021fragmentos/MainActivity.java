package afens.pr021fragmentos;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements UnoFragment.Callback{
    FragmentManager gestor;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestor = getSupportFragmentManager();
        btn= (Button) findViewById(R.id.btn);
        asignarFragmento(R.id.flHueco,"Hola");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.flHuecoSecundaria)==null)
                    SecundariaActivity.start(MainActivity.this,"Cambiado");
                else
                    asignarFragmento(R.id.flHuecoSecundaria,"EHHHH");
            }
        });

    }

    private void asignarFragmento(int a,String mensaje) {
        FragmentTransaction transaction= gestor.beginTransaction();
        transaction.replace(a, UnoFragment.newInstance(mensaje));
        transaction.commit();
    }

    @Override
    public void pulsado(String mensaje) {
        if(findViewById(R.id.flHuecoSecundaria)==null)
            SecundariaActivity.start(MainActivity.this,mensaje);
        else
            asignarFragmento(R.id.flHuecoSecundaria,mensaje);
    }
}
