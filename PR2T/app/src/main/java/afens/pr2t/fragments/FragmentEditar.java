package afens.pr2t.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import afens.pr2t.R;
import afens.pr2t.SQLite.DAO;
import afens.pr2t.modelos.Alumno;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Afens on 27/02/2016.
 */
public class FragmentEditar extends Fragment {
    @Bind(R.id.txtNombre)
    TextView txtNombre;
    @Bind(R.id.imgFoto)
    ImageView imgFoto;
    @Bind(R.id.txtTelefono)
    EditText txtTelefono;
    @Bind(R.id.txtEmail)
    EditText txtEmail;
    @Bind(R.id.txtEmpresa)
    EditText txtEmpresa;
    @Bind(R.id.txtTutor)
    EditText txtTutor;
    @Bind(R.id.txtHorario)
    EditText txtHorario;
    @Bind(R.id.txtDireccion)
    EditText txtDireccion;
    public static final int RC_SELECCIONAR_FOTO = 10;
    public static final int RC_CAPTURAR_FOTO = 11;
    private static final String ALUMNO_A_EDITAR = "alumno_a_editar";
    private Alumno alumno;
    private String sPathFotoOriginal="";
    private String sRutaArchivo="";


    public static FragmentEditar newInstance(Alumno alumno) {
        Bundle args = new Bundle();
        args.putParcelable(ALUMNO_A_EDITAR, alumno);
        FragmentEditar fragment = new FragmentEditar();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_editar, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Si se ha entrado a editar un alumno, se cargará sus datos en los editText.
        if (getArguments() != null) {
            if ((alumno = getArguments().getParcelable(ALUMNO_A_EDITAR)) != null)
                cargarDatosAlumno();
        } else
            alumno = new Alumno();
    }



    //Cargar los datos del alumno
    private void cargarDatosAlumno() {
        txtNombre.setText(alumno.getNombre());
        txtTelefono.setText(alumno.getTelefono());
        txtEmail.setText(alumno.getEmail());
        txtEmpresa.setText(alumno.getEmpresa());
        txtTutor.setText(alumno.getTutor());
        txtHorario.setText(alumno.getHorario());
        txtDireccion.setText(alumno.getDireccion());
        if (alumno.getFoto().isEmpty())
            imgFoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
        else
            Picasso.with(getContext()).load(new File(alumno.getFoto())).error(R.drawable.ic_person).into(imgFoto);
    }

    //Guarda los datos del alumno
    private void guardarDatosAlumno() {
        String ruta="";
        alumno.setNombre(txtNombre.getText().toString());
        alumno.setTelefono(txtTelefono.getText().toString());
        alumno.setEmail(txtEmail.getText().toString());
        alumno.setEmpresa(txtEmpresa.getText().toString());
        alumno.setTutor(txtTutor.getText().toString());
        alumno.setHorario(txtHorario.getText().toString());
        alumno.setDireccion(txtDireccion.getText().toString());

        if (!sRutaArchivo.isEmpty())
            ruta=sRutaArchivo;
        alumno.setFoto(ruta);
    }

    private boolean validar(){
        boolean salida = true;
        if(txtNombre.getText().toString().isEmpty()){
            txtNombre.setError(getActivity().getString(R.string.errorNombre));
            salida = false;
        }
        if(txtTutor.getText().toString().isEmpty()){
            txtTutor.setError(getActivity().getString(R.string.errorTutor));
            salida = false;
        }
        if(txtEmpresa.getText().toString().isEmpty()){
            txtEmpresa.setError(getActivity().getString(R.string.errorEmpresa));
            salida = false;
        }
        if(txtHorario.getText().toString().isEmpty()){
            txtHorario.setError(getActivity().getString(R.string.errorHorario));
            salida = false;
        }
        if(txtTelefono.getText().toString().isEmpty()){
            txtTelefono.setError(getActivity().getString(R.string.errorTelefono));
            salida = false;
        }
        if(txtDireccion.getText().toString().isEmpty()){
            txtDireccion.setError(getActivity().getString(R.string.errorDireccion));
            salida = false;
        }
        return salida;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    // ------------------------------ Menu --------------------------
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (validar()) {
                    guardarDatosAlumno();
                    //Crear
                    if (alumno.getId() == 0) {
                        DAO.getInstance(getContext()).createAlumno(alumno);
                    }
                    //Actualizar
                    else
                        DAO.getInstance(getContext()).updateAlumno(alumno);

                    //getActivity().onBackPressed();
                }
                break;
        }
        return true;
    }

    // ------------------------ Multimedia ---------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case RC_CAPTURAR_FOTO:
                    // Se agrega la foto a la Galería
                    agregarFotoAGaleria(sPathFotoOriginal);
                    // Se escala la foto, se almacena en archivo propio y se muestra en ImageView.
                    cargarImagenEscalada(sPathFotoOriginal);
                    break;
                case RC_SELECCIONAR_FOTO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = data.getData();
                    sPathFotoOriginal = getRealPath(uriGaleria);
                    // Se escala la foto, se almacena en archivo propio y se muestra en ImageView.
                    cargarImagenEscalada(sPathFotoOriginal);
                    break;
            }
        }
    }

    // Obtiene el path real de una imagen a partir de la URI de Galería obtenido con ACTION_PICK.
    private String getRealPath(Uri uriGaleria) {
        // Se consulta en el content provider de la galería el path real del archivo de la foto.
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getActivity().getContentResolver().query(uriGaleria, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String path = c.getString(columnIndex);
        c.close();
        return path;
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
                directorio = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            }
        } else {
            // En almacenamiento interno.
            directorio = getActivity().getFilesDir();
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

    // Agrega a la Galería la foto indicada.
    private void agregarFotoAGaleria(String pathFoto) {
        // Se crea un intent implícito con la acción de
        // escaneo de un fichero multimedia.
        Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // Se obtiene la uri del archivo a partir de su path.
        File archivo = new File(pathFoto);
        Uri uri = Uri.fromFile(archivo);
        // Se establece la uri con datos del intent.
        i.setData(uri);
        // Se envía un broadcast con el intent.
        getActivity().sendBroadcast(i);
    }

    // Escala y muestra la imagen en el visor.
    private void cargarImagenEscalada(String pathFoto) {
        // Se utiliza una tarea asíncrona, para escalar, guardar en archivo propio y mostrar
        // la foto en el ImageView.
        MostrarFotoAsyncTask tarea = new MostrarFotoAsyncTask();
        tarea.execute(pathFoto);
    }


    public void solicitarFoto() {
        FotoDialogFragment frgMiDialogo = new FotoDialogFragment();
        frgMiDialogo.show(getActivity().getSupportFragmentManager(), "DialogFoto");
    }

    private class MostrarFotoAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // Se escala la foto, cuyo path corresponde al primer parámetro,
            // retornado el Bitmap correspondiente.
            return escalarFoto(
                    params[0],
                    getResources().getDimensionPixelSize(R.dimen.ancho_foto),
                    getResources().getDimensionPixelSize(R.dimen.alto_foto));
        }

        // Una vez finalizado el hilo de trabajo. Se ejecuta en el hilo
        // principal. Recibe el Bitmap de la foto escalada (o null si error).
        @Override
        protected void onPostExecute(Bitmap bitmapFoto) {
            if (bitmapFoto != null) {
                // Se guarda la copia propia de la imagen.
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
                String nombre = "IMG_" + timestamp + "_" + ".jpg";
                File archivo = crearArchivoFoto(nombre, false);
                if (archivo != null) {
                    sRutaArchivo=archivo.getAbsolutePath();
                    if (guardarBitmapEnArchivo(bitmapFoto, archivo)) {
                        // Se muestra la foto en el ImageView.
                        imgFoto.setImageBitmap(bitmapFoto);
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
