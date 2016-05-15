package com.kun.movieisawesome;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.movieisawesome.dummy.DummyContent.DummyItem;
import com.kun.movieisawesome.model.ModelConfigImage;
import com.kun.movieisawesome.model.ModelMovie;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_MODEL_NAME = "modelName";
    // TODO: Customize parameters
    private String mModelName;
    private OnListFragmentInteractionListener mListener;
    private ModelConfigImage modelConfigImage;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(String modelName) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODEL_NAME, modelName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mModelName = getArguments().getString(ARG_MODEL_NAME);
        }

        if( modelConfigImage == null ){
            String configImage = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Constants.PREF_CONFIG_IMAGE, null);
            if( configImage != null ){
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<ModelConfigImage> jsonAdapter = moshi.adapter(ModelConfigImage.class);
                try {
                    Log.i("LOG","is model " + configImage);
                    modelConfigImage = jsonAdapter.fromJson(configImage);
                    if( modelConfigImage == null )
                        Log.i("LOG","is model STILL NULL" );
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("LOG","parsing error");
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            String requestUrl = Constants.BASE_URL + Constants.CATE_MOVIE + Constants.GET_POPULAR + "?" + Constants.ATTACH_API_KEY;
            Request request = new Request.Builder().url(requestUrl).build();

            NetworkRequest.instantiateClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    Log.i("LOG","result = " + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String contentString = jsonObject.getJSONArray("results").toString();

                        Moshi moshi = new Moshi.Builder().build();
                        Type listMyData = Types.newParameterizedType(List.class, ModelMovie.class);
                        JsonAdapter<List<ModelMovie>> adapter = moshi.adapter(listMyData);
                        final List<ModelMovie> list = adapter.fromJson(jsonObject.getJSONArray(Constants.RESP_JSON_KEY_RESULTS).toString());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(new MyItemRecyclerViewAdapter(list , mListener, modelConfigImage));
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
