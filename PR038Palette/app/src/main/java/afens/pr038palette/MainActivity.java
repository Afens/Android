package afens.pr038palette;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    private Toolbar toolbar;
    private Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img = (ImageView) findViewById(R.id.img);
        rnd=new Random();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFoto();
            }
        });

        cargarFoto();


    }

    private void cargarFoto() {
        Picasso.with(this).load("http://lorempixel.com/400/200/abstract/"+rnd.nextInt(10)+"/")
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {
                        obtenerPaleta();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    private void obtenerPaleta() {
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // La paleta de 16 colores ya ha sido generada asíncronamente.
                Palette.Swatch swatch = palette.getVibrantSwatch();
                toolbar.setBackgroundColor(swatch.getRgb());
                toolbar.setTitleTextColor(swatch.getTitleTextColor());
                setStatusBarcolor(getWindow(), palette.getDarkVibrantColor(swatch.getRgb()));
            }
        });
    }

    // Establece el color de fondo de la status bar (API > 21).
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setStatusBarcolor(Window window, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
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
}
