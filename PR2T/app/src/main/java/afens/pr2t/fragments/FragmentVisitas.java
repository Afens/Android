package afens.pr2t.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import afens.pr2t.R;
import afens.pr2t.SQLite.DAO;
import afens.pr2t.actividades.CrearVisitaActivity;
import afens.pr2t.adaptadores.GenericAdapter;
import afens.pr2t.adaptadores.ProximasAdapter;
import afens.pr2t.adaptadores.VisitasAdapter;
import afens.pr2t.modelos.Alumno;
import afens.pr2t.modelos.Visita;


/**
 * Created by Afens on 27/02/2016.
 */
public class FragmentVisitas extends Fragment implements GenericAdapter.OnVisitaClickListener {
    private static final String STATE_VISITA = "stateVisitas";
    private static final String ARG_ALUMNO = "alumno";
    public static final int RC_VISITAS = 7;
    private GenericAdapter mAdaptador;
    private ArrayList<Visita> mVisitas = new ArrayList<>();
    private RecyclerView rvVisitas;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onVisitaClick(View view, Visita visita, int position) {
        Intent intent = new Intent(getContext(), CrearVisitaActivity.class);
        intent.putExtra(CrearVisitaActivity.INTENT_VISITA, visita);
        getActivity().startActivityForResult(intent, FragmentVisitas.RC_VISITAS);


    }


    public static FragmentVisitas newInstance(Alumno alumno) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_ALUMNO, alumno);
        FragmentVisitas fragment = new FragmentVisitas();
        fragment.setArguments(args);
        return fragment;
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
        if (getArguments() == null) {
            mAdaptador = new ProximasAdapter(mVisitas);
        }
        //Si existe argumento, se pedira las visitas del alumno pasado por parámetro.
        else {
            mAdaptador = new VisitasAdapter(mVisitas);
        }
        cargarVisitas();
        configRecyclerView();
        configEmptyView();
    }

    private void configEmptyView() {
        RelativeLayout emptyView = (RelativeLayout) getActivity().findViewById(R.id.emptyView);
        mAdaptador.setEmptyView(emptyView);
        ImageView imgEmptyView = (ImageView) getActivity().findViewById(R.id.imgEmptyView);
        TextView lblEmptyView = (TextView) getActivity().findViewById(R.id.lblEmptyView);
        imgEmptyView.setImageResource(R.drawable.ic_sin_visitas);
        lblEmptyView.setText(R.string.sinVisitas);
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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_VISITA, mVisitas);
        super.onSaveInstanceState(outState);
    }


    public void cargarVisitas() {
        if (getArguments() == null) {
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAllProxVisitas());
        } else
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAlumnoVisitas(((Alumno) getArguments().getParcelable(ARG_ALUMNO)).getId()));
    }


}
