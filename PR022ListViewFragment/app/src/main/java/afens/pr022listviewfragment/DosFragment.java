package afens.pr022listviewfragment;

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
    public static Fragment newInstance(Contacto extra) {
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
        Picasso.with(getContext()).load(contacto.getFoto()).into(ivFoto);
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
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + lblTelf.getText()));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getActivity(), "No se pudo realizar la accion", LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
