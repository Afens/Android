package afens.pr033preferencias;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Usuario on 29/01/2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
