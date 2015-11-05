package afens.pr019listviewseleccion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ImageView imgImagen;
    private Button btnComprobar;
    private ListView lstColores;
    private TextView lblWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        lblWin = (TextView) findViewById(R.id.lblWin);
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
        btnComprobar = (Button) findViewById(R.id.btnComprobar);
        lstColores = (ListView) findViewById(R.id.lstColores);
        Picasso.with(this).load("http://3.bp.blogspot.com/-cIWTgxsWtbo/VdFHVTOTTGI/AAAAAAAAAIo/Ed_mwoHy2yM/s1600/caballo%2Bblanco%2B2.jpg").resize(200,200).into(imgImagen);

        ArrayList<String> respuestas = new ArrayList<String>( Arrays.asList (getResources().getStringArray(R.array.respuestas)));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, respuestas);
        lstColores.setAdapter(adapter);
        lstColores.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstColores.setOnItemClickListener(this);
        btnComprobar.setEnabled(false);
        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posSelecionado = lstColores.getCheckedItemPosition();
                String elemento = (String) lstColores.getItemAtPosition(posSelecionado);
                if (TextUtils.equals(elemento, "Blanco")) {
                    lblWin.setVisibility(View.VISIBLE);
                } else {
                    lstColores.setItemChecked(posSelecionado, false);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se activa el botón de comprobación.
        btnComprobar.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflador = getMenuInflater();
        inflador.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu1:
                return true;
            case R.id.mnu2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int posSelecionado = lstColores.getCheckedItemPosition();
        String elemento = (String) lstColores.getItemAtPosition(posSelecionado);

        MenuItem item = menu.findItem(R.id.mnu1);
        item.setTitle(getString(R.string.mnu1,elemento));

        item = menu.findItem(R.id.mnu2);
        item.setTitle(getString(R.string.mnu2, elemento));
        return super.onPrepareOptionsMenu(menu);
    }
}
