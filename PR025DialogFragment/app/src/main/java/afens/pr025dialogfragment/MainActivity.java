package afens.pr025dialogfragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        MiDialogFragment.MiDialogListener, MiDialogFragment2.MiDialogListener2, MiEquipoFragment.MiEquipoListener,
        MiEquipo2Fragment.MiEquipo2Listener, MiEquipo3Fragment.MiEquipo3Listener, MiCitaFragment.MiCitaListener {

    @Bind(R.id.txtCita)
    EditText txtCita;
    @Bind(R.id.date)
    EditText date;
    @Bind(R.id.time)
    EditText time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void algo() {
        DialogFragment frgMiDialogo = new MiDialogFragment();
        frgMiDialogo.show(getSupportFragmentManager(), "MiDialogFragment");
    }

    @OnClick(R.id.button2)
    public void algo2() {
        DialogFragment frgMiDialogo = new MiDialogFragment2();
        frgMiDialogo.show(getSupportFragmentManager(), "MiDialogFragment2");
    }

    @OnClick(R.id.button3)
    public void algo3() {
        DialogFragment frgMiDialogo = new MiEquipoFragment();
        frgMiDialogo.show(getSupportFragmentManager(), "MiEquipo");
    }

    @OnClick(R.id.button4)
    public void algo4() {
        DialogFragment frgMiDialogo = new MiEquipo2Fragment();
        frgMiDialogo.show(getSupportFragmentManager(), "MiEquipo");
    }

    @OnClick(R.id.button5)
    public void algo5() {
        DialogFragment frgMiDialogo = new MiEquipo3Fragment();
        frgMiDialogo.show(getSupportFragmentManager(), "MiEquipo");
    }
    @OnFocusChange(R.id.txtCita)
    public void algo6() {
        if(txtCita.hasFocus()) {
            DialogFragment frgMiDialogo = new MiCitaFragment();
            frgMiDialogo.show(getSupportFragmentManager(), "MiEquipo");
        }
    }
    @OnFocusChange(R.id.date)
    public void algo7() {
        if(date.hasFocus()){
        DialogFragment frgMiDialogo = new MiDateFragment();
        frgMiDialogo.show(getSupportFragmentManager(), "MiEquipo");
        }
    }
    @OnFocusChange(R.id.time)
    public void algo8() {
        if(time.hasFocus()) {
            DialogFragment frgMiDialogo = new MiTimeFragment();
            frgMiDialogo.show(getSupportFragmentManager(), "MiEquipo");
        }
    }


    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        Toast.makeText(this, "El Boton ha sido pulsado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "El tio dijo q si", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "El tio dijo q no", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEquipoSeleccionado(DialogFragment dialog, String equipo) {
        Toast.makeText(this, "Equipo selecionado " + equipo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCita(DialogFragment dialog, String cita) {
        txtCita.setText(cita);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        date.setText(String.format("%02d/%02d/%04d", day, month + 1, year));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        time.setText(String.format("%02d:%02d", hour, min));
    }
}
