package afens.pr2t;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Afens on 28/02/2016.
 */
public class App extends Application {

    public static final String ORDEN_INICIO = "Inicio";
    public static final String ORDEN_FIN = "Fin";
    public static final String FRG_INI_TUTORIAS = "Tutorias";
    public static final String FRG_INI_PROX_VISITAS = "Proximas visitas";
    public static final String PREF_FRAGMENTO_INICIAL = "prefFragmentoInicial";
    public static final String PREF_ORDEN_VISITA = "prefOrdenVisitas";
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
