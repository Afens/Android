package afens.trabajo2t.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import afens.trabajo2t.R;
import afens.trabajo2t.modelos.Alumno;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Afens on 27/02/2016.
 */
public class FragmentEditar extends Fragment {
    @Bind(R.id.txtNombre)
    TextView txtNombre;
    @Bind(R.id.imgFoto)
    ImageView imgFoto;
    @Bind(R.id.txtTelefono)
    EditText txtTelefono;
    @Bind(R.id.txtEmail)
    EditText txtEmail;
    @Bind(R.id.txtEmpresa)
    EditText txtEmpresa;
    @Bind(R.id.txtTutor)
    EditText txtTutor;
    @Bind(R.id.txtHorario)
    EditText txtHorario;
    @Bind(R.id.txtDireccion)
    EditText txtDireccion;

    private static final String ALUMNO_A_EDITAR = "alumno_a_editar";
    private Alumno alumno;


    public static FragmentEditar newInstance(Alumno alumno) {
        Bundle args = new Bundle();
        args.putParcelable(ALUMNO_A_EDITAR, alumno);
        FragmentEditar fragment = new FragmentEditar();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Si se ha entrado a editar un alumno, se cargará sus datos en los editText.
        if (getArguments() != null)
            if ((alumno = getArguments().getParcelable(ALUMNO_A_EDITAR)) != null)
                cargarDatosAlumno();

        configFab();
    }

    private void configFab() {

    }


    //Cargar los datos del alumno
    private void cargarDatosAlumno() {
        txtNombre.setText(alumno.getNombre());
        txtTelefono.setText(alumno.getTelefono());
        txtEmail.setText(alumno.getEmail());
        txtEmpresa.setText(alumno.getEmpresa());
        txtTutor.setText(alumno.getTutor());
        txtHorario.setText(alumno.getHorario());
        txtDireccion.setText(alumno.getDireccion());
        if (alumno.getFoto().isEmpty())
            imgFoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
        else
            Picasso.with(getContext()).load(alumno.getFoto()).error(R.drawable.ic_person).into(imgFoto);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
