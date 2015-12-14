package afens.mislibrosfavoritos;

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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Usuario on 11/12/2015.
 */
public class ListaFragment extends Fragment {
    @Bind(R.id.lstLibros)
    ListView lstLibros;
    @Bind(R.id.lblNoHayLibros)
    TextView lblNoHayLibros;
    private Callback listener;
    private Adaptador adaptador;

    public interface Callback {
        public void verSinopsis(String Titulo, String Sinopsis);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_fragment, container, false);
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
        config();

        super.onActivityCreated(savedInstanceState);
    }

    private void config() {
        lstLibros.setEmptyView(lblNoHayLibros);
        adaptador=new Adaptador(getContext(),Coleccion.getLista());
        lstLibros.setAdapter(adaptador);
        lstLibros.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lstLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Libro libro = Coleccion.getLista().get(i);
                listener.verSinopsis(libro.getTitulo(), libro.getSinopsis());
            }
        });
        lstLibros.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                actionMode.setTitle(lstLibros.getCheckedItemCount()
                        + " de "
                        + lstLibros.getCount());
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.multi_choice_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.mnuEliminar) {

                    // Se obtienen los elementos seleccionados (y se
                    // quita la selección).
                    ArrayList<Libro> elems = getElementosSeleccionados(lstLibros);
                    // Se eliminan del adaptador.
                    for (Libro elemento : elems) {
                        adaptador.remove(elemento);
                    }
                    adaptador.notifyDataSetChanged();
                    Toast.makeText(getContext(),
                            elems.size() + getContext().getString(R.string.dell),
                            Toast.LENGTH_LONG).show();
                    return true;
                } else
                    return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }

    private ArrayList<Libro> getElementosSeleccionados(ListView lstLibros) {
        ArrayList<Libro> datos = new ArrayList<>();
        // Se obtienen los elementos seleccionados de la lista.
        SparseBooleanArray selec = lstLibros.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // Si está seleccionado.
            if (selec.valueAt(i)) {
                int position = selec.keyAt(i);
                // Se quita de la selección.
                lstLibros.setItemChecked(position, false);
                // Se añade al resultado.
                datos.add((Libro) lstLibros.getItemAtPosition(selec.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }

    public void actualizar() {
        adaptador.notifyDataSetChanged();
    }
}
