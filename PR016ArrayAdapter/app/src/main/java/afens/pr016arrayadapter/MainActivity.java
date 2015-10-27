package afens.pr016arrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView lstAlumnos;
    ArrayList<String> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        lstAlumnos=(ListView) findViewById(R.id.lstAlumnos);
        alumnos=new ArrayList<String>();
        alumnos.add("1");
        alumnos.add("2");
        alumnos.add("3");
        alumnos.add("4");
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alumnos);
        lstAlumnos.setAdapter(adaptador);
        lstAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View view, int position, long l) {
                String nombre = (String) lst.getItemAtPosition(position);
                enviar(nombre);
            }
        });

    }

    private void enviar(String nombre) {
        OtraActivity.start(this, nombre);
    }
}
