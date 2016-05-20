package com.kun.movieisawesome;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private static final String ARG_QUERY = "query";
    // TODO: Customize parameters
    private RecyclerView mRecyclerView;

    private String mModelClassName;
    private Class<?> mModelClass;
    private String mReqUrl;
    private String mQuery;

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
    public static <TA extends ModelGeneral> ItemFragment newInstance(String modelClassName, String query) {
        ItemFragment fragment = new ItemFragment<TA>();
        Bundle args = new Bundle();
        fragment.mModelClassName = modelClassName;
        args.putString(ARG_MODEL_CLASS_NAME, modelClassName);
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mModelClassName = getArguments().getString(ARG_MODEL_CLASS_NAME);
            mQuery = getArguments().getString(ARG_QUERY);
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
                fetchRemoteDataUpdateView(mReqUrl != null ?
                        mReqUrl + Constants.ATTACH_API_KEY_AS_FIRST_PARAM +
                                generateQueryParam() + "&page=" + (++page): "");
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
        fetchRemoteDataUpdateView(mReqUrl != null ? mReqUrl + Constants.ATTACH_API_KEY_AS_FIRST_PARAM + generateQueryParam() : "");
    }

    private String generateQueryParam(){
        if( mQuery != null && !mQuery.isEmpty()){
            return "&query=" + mQuery;
        }

        return "";
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

        try {
            String fragmentTag = getTag();
            Class<?> modelClass = Class.forName(mModelClassName);
            if (modelClass != null) {
                Object object = modelClass.newInstance();
                if (object instanceof ModelGeneral) {
                    if (fragmentTag.equals(Constants.TAG_FRAG_REQLIST)) {
                        mReqUrl = ((ModelGeneral) object).getRequestPopularUrl();
                    } else if (fragmentTag.equals(Constants.TAG_FRAG_SEARCH)) {
                        mReqUrl = ((ModelGeneral) object).getSearchUrl();
                    }
                }
            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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
        String title = "";
        if( mModelClassName.equals(ModelMovie.class.getName()))
            title = "Popular Movie";
        else if( mModelClassName.equals(ModelTV.class.getName()))
            title = "Popular TV";
        else if( mModelClassName.equals(ModelPeople.class.getName()))
            title =  "Popular Person";

        return title + ((mQuery != null && !mQuery.isEmpty()) ? " with \"" + mQuery + "\"": "");

    }

    public void setmQuery(String mQuery) {
        this.mQuery = mQuery;
    }
}
