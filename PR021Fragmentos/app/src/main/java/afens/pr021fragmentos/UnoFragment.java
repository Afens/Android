package afens.pr021fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Usuario on 12/11/2015.
 */
public class UnoFragment extends Fragment {
    private static final String ARG_MENSAJE = "Mensaje";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uno, container, false);
    }

    public static UnoFragment newInstance(String mensaje) {
        UnoFragment fragment = new UnoFragment();
        Bundle argumentos = new Bundle();

        argumentos.putString(ARG_MENSAJE, mensaje);
        fragment.setArguments(argumentos);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle argumentos = getArguments();
        String mensaje = argumentos.getString(ARG_MENSAJE);
        TextView lblMensjae= (TextView) getView().findViewById(R.id.lblMensaje);
        lblMensjae.setText(mensaje);
        super.onActivityCreated(savedInstanceState);
    }
}
