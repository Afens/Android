package afens.pr025dialogfragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Usuario on 26/11/2015.
 */
public class MiEquipo3Fragment extends DialogFragment {
    private String[] equipos;
    private boolean[] selec;

    public interface MiEquipo3Listener {
        public void onEquipoSeleccionado(DialogFragment dialog, String favoritos);

    }

    MiEquipo3Listener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle("Equipo");
        equipos = getResources().getStringArray(R.array.equipos);
        b.setSingleChoiceItems(equipos, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selec = i;
            }
        });
        b.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onEquipoSeleccionado(MiEquipo3Fragment.this, equipos[selec]);
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
            listener = (MiEquipo3Listener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }
    }
}
