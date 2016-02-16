package afens.pr041audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private MediaPlayer reproductor;
    private FloatingActionButton play;
    private FloatingActionButton pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        play = (FloatingActionButton) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reproductor.isPlaying()) {
                    reproductor.stop();
                }else
                    prepararReproductor();
                //play.setImageResource();
            }
        });
        pause = (FloatingActionButton) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reproductor.isPlaying())
                    reproductor.pause();
                else
                    reproductor.start();

            }
        });
    }

    private void prepararReproductor() {
        // Se crea el reproductor.
        reproductor = new MediaPlayer();
        try {
            // Se establecen sus propiedades.
            reproductor.setDataSource(this, Uri.parse("https://www.youtube.com/audiolibrary_download?vid=f518966af09174ce")); // Fuente.
            reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC); // Música.
            // La actividad actuará como listener cuando el reproductor esté preparado.
            reproductor.setOnPreparedListener(this);
            // Se prepara el reproductor (asíncronamente)
            reproductor.prepareAsync();
        } catch (Exception e) {
            Log.d("Reproductor", "ERROR: " + e.getMessage());
        }
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

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
