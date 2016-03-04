package afens.trabajo2t.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import afens.trabajo2t.adaptadores.GenericAdapter;
import afens.trabajo2t.adaptadores.ProximasAdapter;
import afens.trabajo2t.adaptadores.VisitasAdapter;
import afens.trabajo2t.modelos.Alumno;
import afens.trabajo2t.modelos.Visita;

/**
 * Created by Afens on 27/02/2016.
 */
public class FragmentVisitas extends Fragment implements GenericAdapter.OnVisitaClickListener {
    private static final String STATE_VISITA = "stateVisitas";
    private static final String ARG_ALUMNO = "alumno";
    private GenericAdapter mAdaptador;
    private ArrayList<Visita> mVisitas = new ArrayList<>();
    private RecyclerView rvVisitas;
    private LinearLayoutManager mLayoutManager;
    private OnVisitaSelectedListener mListener;

    @Override
    public void onVisitaClick(View view, Visita visita, int position) {
        ///Posiblemente lo quite
    }


    // Interfaz para notificación de eventos desde el fragmento.
    public interface OnVisitaSelectedListener {
        // Cuando se selecciona un Alumno.
        void onVisitaSelected(Visita visita, int position);
    }

    public static FragmentVisitas newInstance(Alumno alumno) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_ALUMNO, alumno);
        FragmentVisitas fragment = new FragmentVisitas();
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentVisitas newInstance() {
        return new FragmentVisitas();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Si se entra sin argumentos, se pedirá la lista de próximas visitas.
        if(getArguments() == null){
            mAdaptador = new ProximasAdapter(mVisitas);
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAllProxVisitas());
        }
        else{
            mAdaptador = new VisitasAdapter(mVisitas);
            //Si existe argumento, se pedira las visitas del alumno pasado por parámetro.
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAlumnoVisitas(((Alumno) getArguments().getParcelable(ARG_ALUMNO)).getId()));
        }
        configRecyclerView();
        configFab();
    }


    private void configRecyclerView() {
        rvVisitas = (RecyclerView) getActivity().findViewById(R.id.rv);
        rvVisitas.setHasFixedSize(true);

        mAdaptador.setOnItemClickListener(this);
        rvVisitas.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvVisitas.setLayoutManager(mLayoutManager);
        rvVisitas.setItemAnimator(new DefaultItemAnimator());
    }

    private void configFab() {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnVisitaSelectedListener) context;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz necesaria.
            throw new ClassCastException(context.toString() + " must implements OnVisitaSelectedListener");
        }
    }


    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_VISITA, mVisitas);
        super.onSaveInstanceState(outState);
    }
}
