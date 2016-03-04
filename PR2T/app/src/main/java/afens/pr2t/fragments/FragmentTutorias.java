package afens.pr2t.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import afens.pr2t.R;
import afens.pr2t.adaptadores.PagerAdapter;
import afens.pr2t.modelos.Alumno;


/**
 * Created by Afens on 27/02/2016.
 */
public class FragmentTutorias extends Fragment  {

    private static final String ARG_ALUMNO = "alumno";
    private Alumno mAlumno;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) getActivity().findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public int getCurrentPage(){
        return mViewPager.getCurrentItem();
    }
    public Fragment getItem(int position){
        return mSectionsPagerAdapter.getItem(position);
    }
    public int getIdAlumno(){
        return mAlumno.getId();
    }

    public class SectionsPagerAdapter extends PagerAdapter {
        FragmentEditar frgEditor;
        FragmentVisitas frgVisitas;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    if(frgEditor == null)
                        frgEditor = FragmentEditar.newInstance(mAlumno);
                    return frgEditor;

                case 1:
                    if(frgVisitas == null)
                        frgVisitas = FragmentVisitas.newInstance(mAlumno);
                    return frgVisitas;
            }
            return null;
        }
        @Override
        public int getCount() {
            return 2;
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
