package afens.pr022listviewfragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    @Bind(R.id.icoNombre)
    ImageView icoNombre;
    @Bind(R.id.icoCorreo)
    ImageView icoCorreo;
    @Bind(R.id.icoLocalidad)
    ImageView icoLocalidad;
    @Bind(R.id.txtNombre)
    EditText txtNombre;
    @Bind(R.id.tilNombre)
    TextInputLayout tilNombre;
    @Bind(R.id.txtEdad)
    EditText txtEdad;
    @Bind(R.id.tilEdad)
    TextInputLayout tilEdad;
    @Bind(R.id.ivFoto)
    ImageView ivFoto;
    @Bind(R.id.txtTelf)
    EditText txtTelf;
    @Bind(R.id.tilTelf)
    TextInputLayout tilTelf;
    @Bind(R.id.txtCorreo)
    EditText txtCorreo;
    @Bind(R.id.tilCorreo)
    TextInputLayout tilCorreo;
    @Bind(R.id.txtLocalidad)
    EditText txtLocalidad;
    @Bind(R.id.tilLocalidad)
    TextInputLayout tilLocalidad;
    @Bind(R.id.icoTelf)
    ImageView icoTelf;
    private String sNombreArchivo;
    private String foto;
    private Contacto contacto;
    public static final String EXTRA_CONTACTO = "Nuevo_contacto";
    private static final int RC_SELECCIONAR_FOTO = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int i = intent.getIntExtra(EXTRA_CONTACTO, -1);
        if (i < 0) {
            setTitle("Crear Nuevo Contacto");
            contacto = new Contacto();
        } else {
            setTitle("Editar Contacto");
            contacto = ListaContactos.get(i);
        }
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFoto(contacto.hashCode() + new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + ".jpg");
            }
        });
        setupValidacionTelefono();
        setupValidacionEmail();
        setupFocus();
        if (!(i < 0))
            contactoToView();
    }

    private void contactoToView() {
        txtNombre.setText(contacto.getNombre());
        txtEdad.setText(String.format("%d", contacto.getEdad()));
        txtTelf.setText(contacto.getTelf());
        txtCorreo.setText(contacto.getCorreo());
        txtLocalidad.setText(contacto.getLocalidad());
        if (contacto.getFoto() != null) {
            foto = contacto.getFoto();
            Picasso.with(this).load(new File(contacto.getFoto())).into(ivFoto);
        }
    }

    private void guardarContacto() {
        contacto.setNombre(txtNombre.getText().toString());
        if (!TextUtils.isEmpty(txtEdad.getText().toString()))
            contacto.setEdad(Integer.parseInt(txtEdad.getText().toString()));
        contacto.setTelf(txtTelf.getText().toString());
        contacto.setCorreo(txtCorreo.getText().toString());
        contacto.setLocalidad(txtLocalidad.getText().toString());
        contacto.setFoto(foto);
        if (ListaContactos.indexOf(contacto) == -1) {
            ListaContactos.add(contacto);
        }
    }

    private void setupFocus() {
        final int COLOR = getResources().getColor(R.color.primary_dark);
        txtNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    icoNombre.setColorFilter(COLOR);
                else
                    icoNombre.clearColorFilter();
            }
        });
        txtEdad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    icoNombre.setColorFilter(COLOR);
                else
                    icoNombre.clearColorFilter();
            }
        });
        txtTelf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    icoTelf.setColorFilter(COLOR);
                else
                    icoTelf.clearColorFilter();
            }
        });
        txtLocalidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    icoLocalidad.setColorFilter(COLOR);
                else
                    icoLocalidad.clearColorFilter();
            }
        });
        txtCorreo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    icoCorreo.setColorFilter(COLOR);
                else
                    icoCorreo.clearColorFilter();
            }
        });
    }

    // ------------------------- Menu ---------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflador = getMenuInflater();
        inflador.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuConfirm:
                guardarContacto();
                Toast.makeText(this, "Contacto Añadido", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // ----------------------- Comunicacion con otras actividades -----------------------
    public static void startForResult(Activity activity, int rc, int contacto) {
        Intent intent = new Intent(activity, AddActivity.class);
        intent.putExtra(EXTRA_CONTACTO, contacto);
        activity.startActivityForResult(intent, rc);

    }

    public void finish() {
        Intent resultado = new Intent();
        resultado.putExtra(EXTRA_CONTACTO, ListaContactos.indexOf(contacto));
        setResult(RESULT_OK, resultado);
        super.finish();
    }

    //--------------------------- Validaciones ------------------------
    // Configura la validación del teléfono.
    private void setupValidacionTelefono() {
        txtTelf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtTelf.getText().toString())) {
                    if (!isValidPhoneNumber(txtTelf.getText().toString())) {
                        tilTelf.setError("No es un número de teléfono válido");
                    } else {
                        tilTelf.setErrorEnabled(false);
                    }
                } else {
                    tilTelf.setErrorEnabled(false);
                }
            }
        });
    }

    // Configura la validación del email.
    private void setupValidacionEmail() {
        txtCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtCorreo.getText().toString())) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(txtCorreo.getText().toString()).matches()) {
                        tilCorreo.setError("Debe tener el formato usuario@dominio.tipo");
                    } else {
                        tilCorreo.setErrorEnabled(false);
                    }
                } else {
                    tilCorreo.setErrorEnabled(false);
                }
            }
        });
    }

    // Retorna si la cadena recibida es un número de teléfono válido.
    private boolean isValidPhoneNumber(String cadena) {
        if (cadena.length() > 0 && cadena.length() < 9) return false;
        if (!cadena.startsWith("6") && !cadena.startsWith("7") &&
                !cadena.startsWith("8") && !cadena.startsWith("9"))
            return false;
        return true;
    }


    // --------------------------- Para coger la foto ---------------------------------
    // Copiado de la Wikipedro

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SELECCIONAR_FOTO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = data.getData();
                    String sPathFotoOriginal = getRealPath(uriGaleria);
                    // Se escala la foto, se almacena en archivo propio y se muestra en ImageView.
                    cargarImagenEscalada(sPathFotoOriginal);
                    break;
            }
        }
    }


    // Envía un intent implícito para seleccionar una foto de la galería.
    // Recibe el nombre que debe tomar el archivo con la foto escalada y guardada en privado.
    private void seleccionarFoto(String nombreArchivoPrivado) {
        // Se guarda el nombre para uso posterior.
        sNombreArchivo = nombreArchivoPrivado;
        // Se seleccionará un imagen de la galería.
        // (el segundo parámetro es el Data, que corresponde a la Uri de la galería.)
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, RC_SELECCIONAR_FOTO);
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

    // Escala y muestra la imagen en el visor.
    private void cargarImagenEscalada(String pathFoto) {
        // Se utiliza una tarea asíncrona, para escalar, guardar en archivo propio y mostrar
        // la foto en el ImageView.
        MostrarFotoAsyncTask tarea = new MostrarFotoAsyncTask();
        tarea.execute(pathFoto);
    }

    // Crea un archivo de foto con el nombre indicado en almacenamiento externo si es posible, o si
    // no en almacenamiento interno, y lo retorna. Retorna null si fallo.
    // Si publico es true -> en la carpeta pública de imágenes.
    // Si publico es false, en la carpeta propia de imágenes.
    private File crearArchivoFoto(String nombre, boolean publico) {
        // Se obtiene el directorio en el que almacenarlo.
        File directorio;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (publico) {
                // En el directorio público para imágenes del almacenamiento externo.
                directorio = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            } else {
                directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            }
        } else {
            // En almacenamiento interno.
            directorio = getFilesDir();
        }
        // Su no existe el directorio, se crea.
        if (directorio != null && !directorio.exists()) {
            if (!directorio.mkdirs()) {
                Log.d(getString(R.string.app_name), "error al crear el directorio");
                return null;
            }
        }
        // Se crea un archivo con ese nombre y la extensión jpg en ese
        // directorio.
        File archivo = null;
        if (directorio != null) {
            archivo = new File(directorio.getPath() + File.separator +
                    nombre);
            Log.d(getString(R.string.app_name), archivo.getAbsolutePath());
        }
        // Se retorna el archivo creado.
        return archivo;
    }

    // Tarea asíncrona que obtiene una foto a partir de su path y la muestra en
    // un visor.
    private class MostrarFotoAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // Se escala la foto, cuyo path corresponde al primer parámetro,
            // retornado el Bitmap correspondiente.
            return escalarFoto(
                    params[0],
                    getResources().getDimensionPixelSize(R.dimen.tamAvatar),
                    getResources().getDimensionPixelSize(
                            R.dimen.tamAvatar));
        }

        // Una vez finalizado el hilo de trabajo. Se ejecuta en el hilo
        // principal. Recibe el Bitmap de la foto escalada (o null si error).
        @Override
        protected void onPostExecute(Bitmap bitmapFoto) {
            if (bitmapFoto != null) {
                // Se guarda la copia propia de la imagen.
                File archivo = crearArchivoFoto(sNombreArchivo, false);
                if (archivo != null) {
                    if (guardarBitmapEnArchivo(bitmapFoto, archivo)) {
                        // Se almacena el path de la foto a mostrar en el ImageView.
                        foto = archivo.getAbsolutePath();
                        // Se muestra la foto en el ImageView.
                        ivFoto.setImageBitmap(bitmapFoto);
                    }
                }
            }
        }


        // Escala la foto indicada, para ser mostarda en un visor determinado.
        // Retorna el bitmap correspondiente a la imagen escalada o null si
        // se ha producido un error.
        private Bitmap escalarFoto(String pathFoto, int anchoVisor,
                                   int altoVisor) {
            try {
                // Se obtiene el tamaño de la imagen.
                BitmapFactory.Options opciones = new BitmapFactory.Options();
                opciones.inJustDecodeBounds = true; // Solo para cálculo.
                BitmapFactory.decodeFile(pathFoto, opciones);
                int anchoFoto = opciones.outWidth;
                int altoFoto = opciones.outHeight;
                // Se obtiene el factor de escalado para la imagen.
                int factorEscalado = Math.min(anchoFoto / anchoVisor, altoFoto
                        / altoVisor);
                // Se escala la imagen con dicho factor de escalado.
                opciones.inJustDecodeBounds = false; // Se escalará.
                opciones.inSampleSize = factorEscalado;
                return BitmapFactory.decodeFile(pathFoto, opciones);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // Guarda el bitamp de la foto en un archivo. Retorna si ha ido bien.
        private boolean guardarBitmapEnArchivo(Bitmap bitmapFoto, File archivo) {
            try {
                FileOutputStream flujoSalida = new FileOutputStream(
                        archivo);
                bitmapFoto.compress(Bitmap.CompressFormat.JPEG, 100, flujoSalida);
                flujoSalida.flush();
                flujoSalida.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
