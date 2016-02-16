package afens.pr039fotos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    static final int RC_CAPTURAR_FOTO = 1;
    private static final int RC_SELECCIONAR_FOTO = 2;
    private ImageView img;

    private String sPathFotoOriginal;

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

        img= (ImageView) findViewById(R.id.img);
    }

    private void solicitarCapturaFoto() {
        // Se crea el intent implícito para realizar la acción.
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Si hay alguna actividad que sepa realizar la acción.
        if (i.resolveActivity(getPackageManager()) != null) {
            // Se envía el intent esperando respuesta.
            startActivityForResult(i, RC_CAPTURAR_FOTO);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si todo ha ido bien en la captura.
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_CAPTURAR_FOTO:
                    // Se obtiene la miniatura desde el extra del intent de resultado.
                    Bundle extras = data.getExtras();
                    Bitmap miniatura = (Bitmap) extras.get("data");
                    // Se muestra en el ImageView.
                    img.setImageBitmap(miniatura);
                    break;
                case RC_SELECCIONAR_FOTO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = data.getData();
                    sPathFotoOriginal = getRealPath(uriGaleria);
                    // Se escala la foto, se almacena en archivo propio y se muestra en ImageView.
                    //cargarImagenEscalada(sPathFotoOriginal);
                    mostrarFoto(sPathFotoOriginal);
                    break;
            }
        }
    }
    private void mostrarFoto(String pathFoto) {
        // Se obtiene el tamaño de la vista de destino.
        int anchoImageView = img.getWidth();
        int altoImageView = img.getHeight();
        // Se obtiene el tamaño de la imagen.
        BitmapFactory.Options opciones = new BitmapFactory.Options();
        opciones.inJustDecodeBounds = true; // Solo para cálculo.
        BitmapFactory.decodeFile(pathFoto, opciones);
        int anchoFoto = opciones.outWidth;
        int altoFoto = opciones.outHeight;
        // Se obtiene el factor de escalado para la imagen.
        int factorEscalado = Math.min(anchoFoto/anchoImageView,
                altoFoto/altoImageView);
        // Se escala la imagen con dicho factor de escalado.
        opciones.inJustDecodeBounds = false; // Se escalará.
        opciones.inSampleSize = factorEscalado;
        Bitmap foto = BitmapFactory.decodeFile(pathFoto, opciones);
        // Se asigna la foto al ImageView.
        img.setImageBitmap(foto);
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
    // Guarda el bitamp de la foto en un archivo. Retorna si ha ido bien.
    private boolean guardarBitmapEnArchivo(Bitmap bitmapFoto, File archivo) {
        try {
            FileOutputStream flujoSalida = new FileOutputStream(archivo);
            bitmapFoto.compress(Bitmap.CompressFormat.JPEG, 100, flujoSalida);
            flujoSalida.flush();
            flujoSalida.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
