package afens.pr040recortarfotos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SELECCIONAR_FOTO = 1;
    private static final int RC_RECORTAR_FOTO = 2;
    private String sPathFotoOriginal;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFoto();
            }
        });

        img = (ImageView) findViewById(R.id.img);
    }


    // Envía un intent implícito para seleccionar una foto de la galería.
    // Recibe el nombre que debe tomar el archivo con la foto escalada y guardada en privado.
    private void seleccionarFoto() {
        // Se seleccionará un imagen de la galería.
        // (el segundo parámetro es el Data, que corresponde a la Uri de la galería.)
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, RC_SELECCIONAR_FOTO);
    }

    // Envía un intent implícito para recortar la imagen. Recibe el path de la foto a recortar.
    // Si no es posible recortar, se llama a cargarImagenEscalada().
    private void recortarImagen(String pathFoto) {
        // Se guarda el nombre para uso posterior.
        Intent i = new Intent("com.android.camera.action.CROP");
        i.setDataAndType(Uri.fromFile(new File(pathFoto)), "image/*");
        // Si hay alguna actividad que sepa realizar la acción de recortar.
        if (i.resolveActivity(getPackageManager()) != null) {
            i.putExtra("crop", "true");
            // Ratio.
            i.putExtra("aspectX",
                    getResources().getDimensionPixelSize(R.dimen.ancho_visor));
            i.putExtra("aspectY",
                    getResources().getDimensionPixelSize(R.dimen.alto_visor));
            // Tamaño de salida.
            i.putExtra("outputX",
                    getResources().getDimensionPixelSize(R.dimen.ancho_visor));
            i.putExtra("outputY",
                    getResources().getDimensionPixelSize(R.dimen.alto_visor));
            i.putExtra("return-data", true);
            // Inicio la actividad esperando el resultado.
            startActivityForResult(i, RC_RECORTAR_FOTO);
        }
    }

    // Obtiene el path real de una imagen a partir de la URI de Galería obtenido con ACTION_PICK.
    private String getRealPath(Uri uriGaleria) {
        // Se consulta en el content provider de la galería el path real del archivo de la foto.
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(uriGaleria, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String path = c.getString(columnIndex);
        c.close();
        return path;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SELECCIONAR_FOTO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = intent.getData();
                    sPathFotoOriginal = getRealPath(uriGaleria);
                    // Se recorta la imagen.
                    recortarImagen(sPathFotoOriginal);
                    break;
                case RC_RECORTAR_FOTO:
                    // Se obtiene el bitmap resultante.
                    Bitmap bitmapFotoRecortada = intent.getExtras().getParcelable(
                            "data");

                    img.setImageBitmap(bitmapFotoRecortada);

            }
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
