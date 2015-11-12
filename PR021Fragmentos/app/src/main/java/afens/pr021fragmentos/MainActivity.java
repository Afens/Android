package afens.pr021fragmentos;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    FragmentManager gestor;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestor = getSupportFragmentManager();
        btn= (Button) findViewById(R.id.btn);
        asignarFragmento("Hola");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecundariaActivity.start(MainActivity.this,"Cambiado");
            }
        });

    }

    private void asignarFragmento(String mensaje) {
        FragmentTransaction transaction= gestor.beginTransaction();
        transaction.add(R.id.flHueco, UnoFragment.newInstance(mensaje));
        transaction.commit();
    }
}
