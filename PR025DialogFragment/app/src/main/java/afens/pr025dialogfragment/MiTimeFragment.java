package afens.pr025dialogfragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Usuario on 26/11/2015.
 */
public class MiTimeFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();
        return new TimePickerDialog(this.getActivity(),
                listener, calendario.get(Calendar.HOUR),
                calendario.get(Calendar.MINUTE),
                true);
    }

    // Al vincular el fragmento con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Establecemos la actividad como listener de los eventos del fragmento.
        try {
            listener = (TimePickerDialog.OnTimeSetListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }
    }
}
