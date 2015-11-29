package afens.pr022listviewfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
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
    private ArrayList<Contacto> contactos;
    private Adaptador adaptador;

    //------------------ Interface --------------------
    public interface Callback {
        public void verDetalles(Contacto contacto);
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
    //-------------------------------------------------

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
        lstContactos.setEmptyView(lblNoHayContactos);
        lstContactos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        contactos = crearContactos();
        adaptador = new Adaptador(getContext(), contactos);
        lstContactos.setAdapter(adaptador);
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
        lstContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacto contacto = (Contacto) parent.getItemAtPosition(position);
                listener.verDetalles(contacto);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private ArrayList<Contacto> crearContactos() {
        ArrayList<Contacto> contactos = new ArrayList<>();
        Contacto c;
        for (int i = 0; i < 5; i++) {
            c = new Contacto();
            c.setNombre("C" + i);
            c.setCorreo(i + "@" + i);
            c.setEdad(i);
            c.setLocalidad("Local" + i);
            c.setTelf("+34" + i);
            c.setFoto("http://lorempixel.com/500/500/people/" + (i + 1) + "/");
            contactos.add(c);
        }
        return contactos;
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

}
