package afens.pr2t.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import afens.pr2t.R;
import afens.pr2t.SQLite.DAO;
import afens.pr2t.adaptadores.AlumnosAdapter;
import afens.pr2t.modelos.Alumno;


/**
 * Created by Afens on 27/02/2016.
 */
public class FragmentLista extends Fragment implements AlumnosAdapter.OnItemClickListener {
    private static final String STATE_ALUMNOS = "stateAlumnos";
    private AlumnosAdapter mAdaptador;
    private RecyclerView rvAlumnos;
    private ArrayList<Alumno> mAlumnos = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private FragmentListaListener mListener;

    // Interfaz para notificación de eventos desde el fragmento.
    public interface FragmentListaListener {
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
        //Solo consultará la BDD al cargar el fragmento si no se había cargado de antes.
        //Evita que se consulte cada vez que se gira la pantalla.
        if (savedInstanceState == null)
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAllAlumnos());
        else
            //Recuperará los alumnos de la antigua vez que cargó el fragmento.
            mAdaptador.replaceAll(savedInstanceState.<Alumno>getParcelableArrayList(STATE_ALUMNOS));

    }

    private void configRecyclerView() {
        rvAlumnos = (RecyclerView) getActivity().findViewById(R.id.rv);
        rvAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(mAlumnos);
        mAdaptador.setmListener(this);
        rvAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvAlumnos.setLayoutManager(mLayoutManager);
        rvAlumnos.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {

                    // Cuando se detecta un gesto drag & drop.
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mAdaptador.swapItems(fromPos, toPos);
                        return true;
                    }

                    // Cuando se detecta un gesto swipe to dismiss.
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // Se elimina el elemento.
                        mAdaptador.removeItem(viewHolder.getAdapterPosition());
                    }
                });
        // Se enlaza con el RecyclerView.
        itemTouchHelper.attachToRecyclerView(rvAlumnos);
    }


    //Click en un item del RecyclerView.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        //La actividad que contenga este fragmento, se encargará de que hacer con el alumno seleccionado.
        mListener.onAlumnoSelected(alumno);
    }

    @Override
    public void onDelete(Alumno alumno) {
        DAO.getInstance(null).deleteAlumno(alumno.getId());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FragmentListaListener) context;
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
