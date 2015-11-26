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
public class MiDialogFragment extends DialogFragment {
    public interface MiDialogListener {
        public void onPositiveButtonClick(DialogFragment dialog);
    }
    MiDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(this.getActivity());
        b.setTitle("Pulsacion");
        b.setMessage("Me han pulsado");
        b.setIcon(R.mipmap.ic_launcher);
        b.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            // Al pulsar el botón positivo.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Se notifica el evento al listener.
                listener.onPositiveButtonClick(MiDialogFragment.this);
            }
        });
        return b.create();
    }

    // Al vincular el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establecemos la actividad como listener de los eventos del fragmento.
        try {
            listener = (MiDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }
    }
}
