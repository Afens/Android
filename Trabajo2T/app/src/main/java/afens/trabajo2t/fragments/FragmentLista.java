package afens.trabajo2t.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import afens.trabajo2t.R;
import afens.trabajo2t.SQLite.DAO;
import afens.trabajo2t.adaptadores.AlumnosAdapter;
import afens.trabajo2t.modelos.Alumno;

/**
 * Created by Afens on 27/02/2016.
 */
public class FragmentLista extends Fragment implements AlumnosAdapter.OnItemClickListener {
    private static final String STATE_ALUMNOS = "stateAlumnos";
    private AlumnosAdapter mAdaptador;
    private RecyclerView rvAlumnos;
    private ArrayList<Alumno> mAlumnos = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private OnAlumnoSelectedListener mListener;

    // Interfaz para notificación de eventos desde el fragmento.
    public interface OnAlumnoSelectedListener {
        // Cuando se selecciona un Alumno.
        void onAlumnoSelected(Alumno alumno);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listas, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configRecyclerView();
        configFab();
        //Solo consultará la BDD al cargar el fragmento si no se había cargado de antes.
        //Evita que se consulte cada vez que se gira la pantalla.

        if(savedInstanceState == null)
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAllAlumnos());
        else
            //Recuperará los alumnos de la antigua vez que cargó el fragmento.
            mAdaptador.replaceAll(savedInstanceState.<Alumno>getParcelableArrayList(STATE_ALUMNOS));

    }
    private void configRecyclerView() {
        rvAlumnos = (RecyclerView) getActivity().findViewById(R.id.rv);
        rvAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(mAlumnos);
        mAdaptador.setOnItemClickListener(this);
        rvAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvAlumnos.setLayoutManager(mLayoutManager);
        rvAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

    private void configFab() {

    }

    //Click en un item del RecyclerView.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        //La actividad que contenga este fragmento, se encargará de que hacer con el alumno seleccionado.
        mListener.onAlumnoSelected(alumno);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnAlumnoSelectedListener) context;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz necesaria.
            throw new ClassCastException(context.toString() + " must implements OnAlumnoSeleccionadoListener");
        }
    }


    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_ALUMNOS, mAlumnos);
        super.onSaveInstanceState(outState);
    }
}
