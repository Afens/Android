package afens.trabajo2t.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import afens.trabajo2t.R;
import afens.trabajo2t.adaptadores.PagerAdapter;
import afens.trabajo2t.modelos.Alumno;

/**
 * Created by Afens on 27/02/2016.
 */
public class FragmentTutorias extends Fragment {

    private static final String ARG_ALUMNO = "alumno";
    private FragmentManager mGestor;
    private Alumno mAlumno;

    public static FragmentTutorias newInstance(Alumno alumno) {
        Bundle args = new Bundle();
        FragmentTutorias fragment = new FragmentTutorias();
        args.putParcelable(ARG_ALUMNO, alumno);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorias, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAlumno = getArguments().getParcelable(ARG_ALUMNO);
        configViewPager();
    }
    private void configViewPager() {
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        ViewPager mViewPager = (ViewPager) getActivity().findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends PagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return FragmentEditar.newInstance(mAlumno);
                case 1:
                    return FragmentVisitas.newInstance(mAlumno);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ALUMNO";
                case 1:
                    return "VISITAS";
            }
            return null;
        }

    }
}
