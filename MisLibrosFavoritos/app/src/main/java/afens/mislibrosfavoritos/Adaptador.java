package afens.mislibrosfavoritos;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Usuario on 11/12/2015.
 */
public class Adaptador extends ArrayAdapter<Libro> {
    private final ArrayList<Libro> datos;

    public Adaptador(Context context, ArrayList<Libro> datos) {
        super(context, R.layout.item, datos);
        this.datos = datos;

    }

    // Clase estática contenedora de vistas de fila
    private static class ViewHolder {
        private final TextView lblTitulo;
        private final TextView lblAutor;
        private final TextView lblYear;
        private final ImageView imgPortada;

        public ViewHolder(View itemView) {
            lblTitulo = (TextView) itemView.findViewById(R.id.lblTitulo);
            lblAutor = (TextView) itemView.findViewById(R.id.lblAutor);
            lblYear = (TextView) itemView.findViewById(R.id.lblYear);
            imgPortada = (ImageView) itemView.findViewById(R.id.imgPortada);
        }
    }

    // Método que construye y retorna la vista a usar para mostrar un
    // elemento de la lista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Contenedor de las vistas que conforman la vista-fila.
        ViewHolder holder;
        // Si no hay vista-fila para reciclar, se infla el layut.
        if (convertView == null) {
            // Se obtiene un inflador de vista que se usa para inflar la
            // especificación XML del layout
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item, parent, false);
            // Se crea un nuevo objeto contenedor de las vistas de la vista-fila.
            holder = new ViewHolder(convertView);
            // Se almacena el contenedor en la propiedad Tag de la vista-fila.
            convertView.setTag(holder);
        }
        // Si la vista-fila corresponde a un objeto reciclable.
        else {
            // Se obtienen el objeto contenedor desde la propiedad Tag de la vista-fila.
            holder = (ViewHolder) convertView.getTag();
        }
        // Se "escriben" los datos en dichas vistas. Para obtener el dato
        // concreto se utiliza el parámetro position que actúa como índice del
        // array de datos gestionados por el adaptador.
        onBindViewHolder(holder, position);
        // Se retorna la vista-fila.
        return convertView;
    }

    private void onBindViewHolder(ViewHolder holder, int position) {
        Libro libro = datos.get(position);
        holder.lblTitulo.setText(libro.getTitulo());
        holder.lblAutor.setText(libro.getAutor());
        holder.lblYear.setText(String.format("%d", libro.getYear_publicacion()));

        if (libro.getUrl_portada().length()>0) {
            Picasso.with(getContext().getApplicationContext()).load(libro.getUrl_portada())
                    .error(R.drawable.empty).into(holder.imgPortada);
        } else {
            Picasso.with(getContext().getApplicationContext()).load(R.drawable.empty).into(holder.imgPortada);
        }
    }
}
