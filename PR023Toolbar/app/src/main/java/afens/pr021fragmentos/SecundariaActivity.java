package afens.pr021fragmentos;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecundariaActivity extends AppCompatActivity implements UnoFragment.Callback{
    private static final String EXTRA_MENSAJE = "E_mensaje";
    FragmentManager gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        gestor = getSupportFragmentManager();
        Intent intent= getIntent();
        String mensaje=intent.getStringExtra(EXTRA_MENSAJE);
        asignarFragmento(mensaje);

    }


    public static void start(Context context,String mensaje) {
        Intent intent=new Intent(context,SecundariaActivity.class);
        intent.putExtra(EXTRA_MENSAJE,mensaje);
        context.startActivity(intent);

    }

    private void asignarFragmento(String mensaje) {
        FragmentTransaction transaction= gestor.beginTransaction();
        transaction.add(R.id.flHuecoSecundaria, UnoFragment.newInstance(mensaje));
        transaction.commit();
    }

    @Override
    public void pulsado(String mensaje) {

    }
}
