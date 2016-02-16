package afens.pr042audioservicio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton play;
    private FloatingActionButton pause;
    private Intent intentServicio;
    private LocalBroadcastManager gestor;
    private BroadcastReceiver receptor;
    private IntentFilter filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se crea el intent de inicio del servicio.
        intentServicio = new Intent(getApplicationContext(),
                MusicaOnlineService.class);

        // Se obtiene el gestor de receptores locales.
        gestor = LocalBroadcastManager.getInstance(this);

        // Se crea el filtro para al receptor.
        filtro = new IntentFilter(MusicaOnlineService.ACTION_COMPLETADA);

        play = (FloatingActionButton) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);

                intentServicio.putExtra(MusicaOnlineService.EXTRA_URL_CANCION, Uri.parse("https://www.youtube.com/audiolibrary_download?vid=f518966af09174ce"));
                startService(intentServicio);
            }
        });
        pause = (FloatingActionButton) findViewById(R.id.pause);
        pause.setVisibility(View.INVISIBLE);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.INVISIBLE);
                play.setVisibility(View.VISIBLE);
                stopService(intentServicio);
            }
        });
        // Se crea el receptor de mensajes desde el servicio.
        receptor = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                pause.setVisibility(View.INVISIBLE);
                play.setVisibility(View.VISIBLE);
            }
        };
    }



    @Override
    protected void onResume() {
        super.onResume();
        // Se registra el receptor en el gestor de receptores locales para dicha
        // acción.
        gestor.registerReceiver(receptor, filtro);
    }

    @Override
    protected void onPause() {
        // Se desregistra el receptor del gestor de receptores locales para
        // dicha acción.
        gestor.unregisterReceiver(receptor);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
