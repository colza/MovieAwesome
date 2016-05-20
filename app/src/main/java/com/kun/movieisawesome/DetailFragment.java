package com.kun.movieisawesome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.movieisawesome.databinding.DetailContentBinding;
import com.kun.movieisawesome.model.ModelGeneral;

public class DetailFragment extends MyFragment {
    private ModelGeneral mModelGeneral;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(ModelGeneral modelGeneral) {
        DetailFragment fragment = new DetailFragment();
        fragment.mModelGeneral = modelGeneral;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public String getTitle() {
        return mModelGeneral.getShowTitle();
    }
}
