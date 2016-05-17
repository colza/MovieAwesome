package com.kun.movieisawesome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.movieisawesome.databinding.DetailContentBinding;
import com.kun.movieisawesome.model.ModelGeneral;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ModelGeneral mModelGeneral;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(ModelGeneral modelGeneral) {
        DetailFragment fragment = new DetailFragment();
        fragment.mModelGeneral = modelGeneral;
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DetailContentBinding detailContentBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.detail_content, container, false);
        detailContentBinding.setIsDetail(String.valueOf(Boolean.TRUE));
        detailContentBinding.setModelConfigImage(MainActivity.getsModelConfigImage(getActivity()));
        detailContentBinding.setModelGeneral(mModelGeneral);
        View view = detailContentBinding.getRoot();

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
