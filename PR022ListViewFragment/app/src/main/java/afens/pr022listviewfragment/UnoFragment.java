package afens.pr022listviewfragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Afens on 29/11/2015.
 */
public class UnoFragment extends Fragment {

    @Bind(R.id.lstContactos)
    ListView lstContactos;
    @Bind(R.id.lblNoHayContactos)
    TextView lblNoHayContactos;
    private Callback listener;
    private Adaptador adaptador;

    //------------------ Interface --------------------
    public interface Callback {
        public void verDetalles(int contacto);

        public void solicitarContacto();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("La actividad debe implementar la interfaz Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    //---------------------- Codigo Principal -----------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uno, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        lstContactos.setEmptyView(lblNoHayContactos);
        adaptador = new Adaptador(getContext(), ListaContactos.crearContactos());
        lstContactos.setAdapter(adaptador);

        if (getActivity().getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            lstContactos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        else {
            lstContactos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            lstContactos.setMultiChoiceModeListener(
                    new AbsListView.MultiChoiceModeListener() {
                        @Override
                        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                            // Se actualiza el título de la action bar contextual.
                            mode.setTitle(lstContactos.getCheckedItemCount()
                                    + " de "
                                    + lstContactos.getCount());
                        }

                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            mode.getMenuInflater().inflate(R.menu.multi_choise_menu, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            return false;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            if (item.getItemId() == R.id.mnuEliminar) {

                                // Se obtienen los elementos seleccionados (y se
                                // quita la selección).
                                ArrayList<Contacto> elems = getElementosSeleccionados(lstContactos);
                                // Se eliminan del adaptador.
                                for (Contacto elemento : elems) {
                                    adaptador.remove(elemento);
                                }
                                adaptador.notifyDataSetChanged();
                                Toast.makeText(getContext(),
                                        elems.size() + getContext().getString(R.string.dell),
                                        Toast.LENGTH_LONG).show();
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {

                        }
                    });
        }

        lstContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity().getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    setItemChecked(position);
                listener.verDetalles(position);
            }
        });
        lblNoHayContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.solicitarContacto();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }


    private ArrayList<Contacto> getElementosSeleccionados(ListView lstContactos) {
        ArrayList<Contacto> datos = new ArrayList<>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lstContactos.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                int position = selec.keyAt(i);
                // Se quita de la selección.
                lstContactos.setItemChecked(position, false);
                // Se añade al resultado.
                datos.add((Contacto) lstContactos.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

    //----------------------- Comunicacion Actividad con el Fragmento ----------------------

    public void actualizar() {
        adaptador.notifyDataSetChanged();
    }
    public void setItemChecked(int pos){lstContactos.setItemChecked(pos, true);}



    //------------------------------- Menu -------------------------------------------
    @Override
    // Añade al menu el boton de añadir
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment1_menu, menu);
        if (getActivity().getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.menu.multi_choise_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    // Comprueba si la opcion selecionada del menu es de este fragmento
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuAdd:
                listener.solicitarContacto();
                return true;
            case R.id.mnuEliminar:
                if (getActivity().getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    ArrayList<Contacto> elems = getElementosSeleccionados(lstContactos);
                    // Se eliminan del adaptador.
                    for (Contacto elemento : elems) {
                        adaptador.remove(elemento);
                    }
                    Toast.makeText(getContext(),
                            getContext().getString(R.string.dell),
                            Toast.LENGTH_LONG).show();
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);

        }

    }

}
