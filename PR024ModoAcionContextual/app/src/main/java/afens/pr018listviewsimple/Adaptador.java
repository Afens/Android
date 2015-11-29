package afens.pr018listviewsimple;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Usuario on 29/10/2015.
 */
public class Adaptador extends ArrayAdapter<Alumno> {
    private final ArrayList<Alumno> datos;

    public Adaptador(Context context, ArrayList<Alumno> datos) {
        super(context, R.layout.fila, datos);
        this.datos = datos;

    }


    private static class ViewHolder {
        private final TextView lblNombre;
        private final TextView lblEdad;

        public ViewHolder(View itemView) {
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblEdad = (TextView) itemView.findViewById(R.id.lblEdad);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.fila,parent,false);

            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        onBindViewHolder(holder,position);

        return convertView;
    }

    private void onBindViewHolder(ViewHolder holder, int position) {
        Alumno alumno= datos.get(position);

        holder.lblNombre.setText(alumno.getNombre());
        if (alumno.getEdad()<18)
            holder.lblEdad.setTextColor(Color.RED);
        else
            holder.lblEdad.setTextColor(Color.BLACK);
        holder.lblEdad.setText(String.format("%d años", alumno.getEdad()));

    }
}
