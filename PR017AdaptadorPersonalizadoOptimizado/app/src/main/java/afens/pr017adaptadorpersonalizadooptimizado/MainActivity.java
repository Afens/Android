package afens.pr017adaptadorpersonalizadooptimizado;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private ListView lstAlumnos;
    private ArrayList<Alumno> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        alumnos = new ArrayList<Alumno>();
        alumnos.add(new Alumno("Baldomero", 18, "66666"));
        alumnos.add(new Alumno("Blad", 30, "976756"));
        alumnos.add(new Alumno("pepe", 8, "994884"));
        alumnos.add(new Alumno("Paco", 10, "6677567"));
        alumnos.add(new Alumno("Afens", 22, "6561567"));
        alumnos.add(new Alumno("yo", 22, "6561567"));
        alumnos.add(new Alumno("Baldomero", 18, "66666"));
        alumnos.add(new Alumno("Blad", 30, "976756"));
        alumnos.add(new Alumno("pepe", 8, "994884"));
        alumnos.add(new Alumno("Paco", 10, "6677567"));
        alumnos.add(new Alumno("Afens", 22, "6561567"));
        alumnos.add(new Alumno("yo", 22, "6561567"));
        alumnos.add(new Alumno("Baldomero", 18, "66666"));
        alumnos.add(new Alumno("Blad", 30, "976756"));
        alumnos.add(new Alumno("pepe", 8, "994884"));
        alumnos.add(new Alumno("Paco", 10, "6677567"));
        alumnos.add(new Alumno("Afens", 22, "6561567"));
        alumnos.add(new Alumno("yo", 22, "6561567"));
        Adaptador adaptador = new Adaptador(this, alumnos);
        lstAlumnos.setAdapter(adaptador);
        lstAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lst, View view, int position, long l) {
                Alumno alumno = (Alumno) lstAlumnos.getItemAtPosition(position);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)" + alumno.getTlf()));

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "No se pudo realizar la accion", LENGTH_LONG).show();
                }
            }
        });
    }
}
