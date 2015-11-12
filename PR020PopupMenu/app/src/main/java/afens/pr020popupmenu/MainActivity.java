package afens.pr020popupmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lstContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        lstContactos = (ListView) findViewById(R.id.lstContactos);
        ArrayList<Contacto> alumnos = new ArrayList<>();
        alumnos.add(new Contacto("Baldomero", "2º CFGS DAM", "956956956"));
        alumnos.add(new Contacto("Germán Ginés", "2º CFGS DAM", "678678678"));
        Adaptador adaptador = new Adaptador(this, alumnos);

        lstContactos.setAdapter(adaptador);
    }
}
