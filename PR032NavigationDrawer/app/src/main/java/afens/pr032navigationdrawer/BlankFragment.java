package afens.pr032navigationdrawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class BlankFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";
    private TextView lblFragment;


    public static BlankFragment newInstance(String param) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getArguments() != null) {
            String mParam = getArguments().getString(ARG_PARAM);
            lblFragment = (TextView) getView().findViewById(R.id.lblFragment);
            lblFragment.setText(mParam);
        }
        super.onActivityCreated(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    public void onFabPressed() {
        Snackbar.make(getView(), "Se encuentra en: "+lblFragment.getText(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}
