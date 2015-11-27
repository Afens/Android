package afens.pr025dialogfragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Usuario on 26/11/2015.
 */
public class MiCitaFragment extends DialogFragment {


    public interface MiCitaListener {
        public void onCita(DialogFragment dialog, String cita);

    }

    MiCitaListener listener;
    View vista;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle("Equipo");
        vista = LayoutInflater.from(getActivity()).inflate(
                R.layout.cita_layout, null);
        b.setView(vista);


        b.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                listener.onCita(MiCitaFragment.this, ((EditText) vista.findViewById(R.id.txtEntrada)).getText().toString());
            }
        });
        b.setNegativeButton("No Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        b.setIcon(R.mipmap.ic_launcher);

        return b.create();
    }

    // Al vincular el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establecemos la actividad como listener de los eventos del fragmento.
        try {
            listener = (MiCitaListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }
    }
}
