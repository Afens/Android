package afens.mislibrosfavoritos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Usuario on 11/12/2015.
 */
public class SinopsisFragment extends Fragment {
    private static final String ARG_TITULO = "TITULO";
    private static final String ARG_SINOPSIS = "SINOPSIS";
    @Bind(R.id.lbl_frag_Titulo)
    TextView lblFragTitulo;
    @Bind(R.id.lbl_frag_Sinopsis)
    TextView lblFragSinopsis;
    @Bind(R.id.imgCerrar)
    ImageView imgCerrar;
    private Cerrar listener;
    private String titulo;
    private String sinopsis;

    public interface Cerrar {
        public void cerrar();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (Cerrar) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("La actividad debe implementar la interfaz Cerrar");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public static Fragment newInstance(String titulo, String sinopsis) {
        Fragment fragment = new SinopsisFragment();
        Bundle argumentos = new Bundle();
        argumentos.putString(ARG_TITULO, titulo);
        argumentos.putString(ARG_SINOPSIS, sinopsis);
        fragment.setArguments(argumentos);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sinopsis_fragment, container, false);
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
        Bundle argumentos = getArguments();
        titulo = argumentos.getString(ARG_TITULO);
        sinopsis = argumentos.getString(ARG_SINOPSIS);
        mostrarDatos();
        imgCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cerrar();
            }
        });
        setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }

    private void mostrarDatos() {
        lblFragTitulo.setText(titulo);
        if(sinopsis.length()>0)
            lblFragSinopsis.setText(sinopsis);
        else
            lblFragSinopsis.setText(R.string.no_disponible);
    }
}
