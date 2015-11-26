package afens.pr025dialogfragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Arrays;

/**
 * Created by Usuario on 26/11/2015.
 */
public class MiEquipoFragment extends DialogFragment {
    private String[] equipos;

    public interface MiEquipoListener {
        public void onEquipoSeleccionado(DialogFragment dialog, String equipo);

    }

    MiEquipoListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle("Equipo");
        equipos = getResources().getStringArray(R.array.equipos);
        b.setItems(equipos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onEquipoSeleccionado(MiEquipoFragment.this, equipos[i]);
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
            listener = (MiEquipoListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }
    }
}
