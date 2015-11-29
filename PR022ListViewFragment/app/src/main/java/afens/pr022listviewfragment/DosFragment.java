package afens.pr022listviewfragment;

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

/**
 * Created by Afens on 29/11/2015.
 */
public class DosFragment extends Fragment {

    private static final String ARG_CONTACTO = "Contacto";
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

    // Para crear el Fragmento con argumentos
    public static Fragment newInstance(Parcelable extra) {
        Fragment fragment = new DosFragment();
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(ARG_CONTACTO, extra);
        fragment.setArguments(argumentos);
        return fragment;
    }

    @Nullable
    @Override
    // Retorna la vista que mostrará el fragmento.
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dos, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle argumentos = getArguments();
        Contacto contacto = argumentos.getParcelable(ARG_CONTACTO);
        mostrarDetalles(contacto);
        setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }

    private void mostrarDetalles(Contacto contacto) {
        lblNombre.setText(contacto.getNombre());
        lblEdad.setText(String.format(getActivity().getString(R.string.a), contacto.getEdad()));
        lblTelf.setText(contacto.getTelf());
        lblCorreo.setText(contacto.getCorreo());
        lblLocalidad.setText(contacto.getLocalidad());
        Picasso.with(getContext()).load("http://lorempixel.com/500/500/people/1").resize(150, 150).into(ivFoto);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    // Añade al menu el boton de llamar
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment2_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    // Comprueba si la opcion selecionada del menu es de este fragmento
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuLlamar) {
            //Falta hacer que llame

            Toast.makeText(getActivity(), "Llammando a "+lblTelf.getText(),
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
