package com.kun.movieisawesome;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.movieisawesome.model.ModelGeneral;
import com.kun.movieisawesome.model.ModelMovie;
import com.kun.movieisawesome.model.ModelPeople;
import com.kun.movieisawesome.model.ModelTV;
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
public class ItemFragment<T> extends MyFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_MODEL_CLASS_NAME = "modelClassName";
    private static final String ARG_REQ_URL = "reqUrl";
    // TODO: Customize parameters
    private RecyclerView mRecyclerView;

    private String mModelClassName;
    private Class<?> mModelClass;
    private String mReqUrl;

    private OnListFragmentInteractionListener mListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private int page = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static <TA extends ModelGeneral> ItemFragment newInstance(String modelClassName, String reqUrl) {
        ItemFragment fragment = new ItemFragment<TA>();
        Bundle args = new Bundle();
        fragment.mModelClassName = modelClassName;
        args.putString(ARG_MODEL_CLASS_NAME, modelClassName);
        args.putString(ARG_REQ_URL, reqUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mModelClassName = getArguments().getString(ARG_MODEL_CLASS_NAME);
            mReqUrl = getArguments().getString(ARG_REQ_URL);
        }

        if( mModelClassName != null) {
            try {
                mModelClass = Class.forName(mModelClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        mOnLoadMoreListener = new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                Log.i("LOG","load more");
                fetchRemoteDataUpdateView(mReqUrl != null ? mReqUrl + "&page=" + (++page): "");
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(new MyItemRecyclerViewAdapter(mListener, mOnLoadMoreListener));
            startFirstRequest();
        }
        return view;
    }

    public void startFirstRequest(){
        ((MyItemRecyclerViewAdapter) mRecyclerView.getAdapter()).clearList();
        fetchRemoteDataUpdateView(mReqUrl != null ? mReqUrl : "");
    }

    private void fetchRemoteDataUpdateView(String reqUrl){
        Request request = new Request.Builder().url(reqUrl).build();

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
                    String contentString = jsonObject.getJSONArray(Constants.RESP_JSON_KEY_RESULTS).toString();
                    final List<T> list = transferJArrToList(contentString);
                    if( getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((MyItemRecyclerViewAdapter) mRecyclerView.getAdapter()).attachCollections(list);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<T> transferJArrToList(String jsonArrStr) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        Type listMyData = Types.newParameterizedType(List.class, mModelClass);
        JsonAdapter<List<T>> adapter = moshi.adapter(listMyData);
        return adapter.fromJson(jsonArrStr);
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
        void onListFragmentInteraction(ModelGeneral modelGeneral);
    }

    public interface OnLoadMoreListener{
        void loadMore();
    }

    public String getmModelClassName() {
        return mModelClassName;
    }

    @Override
    public String getTitle() {
        if( mModelClassName.equals(ModelMovie.class.getName()))
            return "Popular Movie";
        else if( mModelClassName.equals(ModelTV.class.getName()))
            return "Popular TV";
        else if( mModelClassName.equals(ModelPeople.class.getName()))
            return "Popular Person";
        else
            return "";
    }

    public void setmReqUrl(String reqUrl) {
        this.mReqUrl = reqUrl;
    }
}
