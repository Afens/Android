package afens.pr022listviewfragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Afens on 29/11/2015.
 */
public class DosFragment extends Fragment {

    private static final String ARG_CONTACTO = "Contacto";
    private static final int RC_EDIT = 2;
    @Bind(R.id.lblNombre)
    TextView lblNombre;
    @Bind(R.id.lblEdad)
    TextView lblEdad;
    @Bind(R.id.ivFoto)
    ImageView ivFoto;
    @Bind(R.id.lblTelf)
    TextView lblTelf;
    @Bind(R.id.lblCorreo)
    TextView lblCorreo;
    @Bind(R.id.lblLocalidad)
    TextView lblLocalidad;
    private Edit listener;
    private Contacto contacto;

    public Contacto getContacto() {
        return contacto;
    }


    //------------------ Interface --------------------
    public interface Edit {
        public void editarContacto(int contacto);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (Edit) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("La actividad debe implementar la interfaz Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    //------------ Llamar al fragmento -------------------

    // Para crear el Fragmento con argumentos
    public static Fragment newInstance(int extra) {
        Fragment fragment = new DosFragment();
        Bundle argumentos = new Bundle();
        argumentos.putInt(ARG_CONTACTO, extra);
        fragment.setArguments(argumentos);
        return fragment;
    }

    @Nullable
    @Override


    // ---------------- Codigo Principal----------------------
    // Retorna la vista que mostrará el fragmento.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dos, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle argumentos = getArguments();
        contacto = ListaContactos.get(argumentos.getInt(ARG_CONTACTO));
        mostrarDetalles();
        setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }

    public void mostrarDetalles() {
        lblNombre.setText(contacto.getNombre());
        lblEdad.setText(String.format(getActivity().getString(R.string.a), contacto.getEdad()));
        lblTelf.setText(contacto.getTelf());
        lblCorreo.setText(contacto.getCorreo());
        lblLocalidad.setText(contacto.getLocalidad());
        Picasso.with(getContext()).load(contacto.getFoto()).into(ivFoto);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    // -------------------- Codigo Menu ------------------------
    @Override
    // Añade al menu el boton de llamar
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment2_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    // Comprueba si la opcion selecionada del menu es de este fragmento
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuLlamar:
                // Realiza la llamada al telefono del contacto
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + lblTelf.getText()));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No se pudo realizar la accion", LENGTH_LONG).show();
                }
                return true;
            case R.id.mnuEditar:
                listener.editarContacto(ListaContactos.indexOf(contacto));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
