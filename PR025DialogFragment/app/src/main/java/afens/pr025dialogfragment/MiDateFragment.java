package afens.pr025dialogfragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Usuario on 26/11/2015.
 */
public class MiDateFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();
        return new DatePickerDialog(this.getActivity(),
                listener, calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));
    }

    // Al vincular el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establecemos la actividad como listener de los eventos del fragmento.
        try {
            listener = (DatePickerDialog.OnDateSetListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }
    }
}
