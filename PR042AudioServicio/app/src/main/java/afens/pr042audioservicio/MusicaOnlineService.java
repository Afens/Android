package afens.pr042audioservicio;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Usuario on 12/02/2016.
 */
public class MusicaOnlineService extends Service implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    public static final String EXTRA_URL_CANCION = "url";
    public static final String ACTION_COMPLETADA = "afens.action_completada";
    private MediaPlayer reproductor;

    @Override
    public void onCreate() {
        super.onCreate();
        // Se crea y configura el reproductor.
        reproductor = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        // Se para la reproducción y se liberan los recursos.
        if (reproductor != null) {
            reproductor.stop();
            reproductor.release();
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Se prepara la reproducción de la canción.
        if (reproductor != null) {
            reproductor.reset();
            reproductor.setLooping(false);
            reproductor.setAudioStreamType(AudioManager.STREAM_MUSIC);
            reproductor.setOnPreparedListener(this);
            reproductor.setOnCompletionListener(this);
            String urlCancion = intent.getStringExtra(EXTRA_URL_CANCION);
            try {
                reproductor.setDataSource(urlCancion);
                reproductor.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // El servicio NO se reiniciará automáticamente si es matado por el
        // sistema.
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // El servicio NO es vinculado.
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        // Se inicia la reproducción.
        reproductor.start();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        // Se envía un intent de respuesta al receptor.
        Intent intentRespuesta = new Intent(ACTION_COMPLETADA);
        // El intent será recibido por un Receiver local registrado en el gestor
        // para dicha acción.
        LocalBroadcastManager gestor = LocalBroadcastManager.getInstance(this);
        gestor.sendBroadcast(intentRespuesta);
        // Se finaliza el servicio.
        stopSelf();
    }

}

