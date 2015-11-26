package afens.pr025dialogfragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MiDialogFragment.MiDialogListener,
        MiDialogFragment2.MiDialogListener2, MiEquipoFragment.MiEquipoListener, MiEquipo2Fragment.MiEquipo2Listener{

    @Bind(R.id.button)
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void algo(){
        DialogFragment frgMiDialogo = new MiDialogFragment();
        frgMiDialogo.show(getSupportFragmentManager(), "MiDialogFragment");
    }
    @OnClick(R.id.button2)
    public void algo2(){
        DialogFragment frgMiDialogo = new MiDialogFragment2();
        frgMiDialogo.show(getSupportFragmentManager(), "MiDialogFragment2");
    }
    @OnClick(R.id.button3)
     public void algo3(){
        DialogFragment frgMiDialogo = new MiEquipoFragment();
        frgMiDialogo.show(getSupportFragmentManager(), "MiEquipo");
    }
    @OnClick(R.id.button4)
    public void algo4(){
        DialogFragment frgMiDialogo = new MiEquipo2Fragment();
        frgMiDialogo.show(getSupportFragmentManager(), "MiEquipo");
    }


    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        Toast.makeText(this,"El Boton ha sido pulsado",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Toast.makeText(this,"El tio dijo q si",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) {
        Toast.makeText(this,"El tio dijo q no",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEquipoSeleccionado(DialogFragment dialog, String equipo) {
        Toast.makeText(this,"Equipo selecionado " + equipo,Toast.LENGTH_SHORT).show();
    }
}
