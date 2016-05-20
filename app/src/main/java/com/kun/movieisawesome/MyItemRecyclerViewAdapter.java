package com.kun.movieisawesome;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.kun.movieisawesome.ItemFragment.OnListFragmentInteractionListener;
import com.kun.movieisawesome.databinding.ListItemBinding;
import com.kun.movieisawesome.model.ModelGeneral;
import com.kun.movieisawesome.model.ModelPeople;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


public class MyItemRecyclerViewAdapter<TM extends ModelGeneral> extends RecyclerView.Adapter<MyItemRecyclerViewAdapter<TM>.BindingHolder> {
    private final List<TM> mValues;
    private final OnListFragmentInteractionListener mOnListFragmentInteractionListener;
    private final ItemFragment.OnLoadMoreListener mOnLoadMoreListener;
    private int lastAnimatedPosition = -1;
    private Handler mHandler;

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener, ItemFragment.OnLoadMoreListener onLoadMoreListener) {
        mValues = new ArrayList<>();
        mOnListFragmentInteractionListener = listener;
        mOnLoadMoreListener = onLoadMoreListener;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void attachCollections(Collection<TM> collection) {
        int currentPosition = getItemCount() == 0 ? 0 : getItemCount() - 1;
        mValues.addAll(collection);
        notifyItemRangeChanged(currentPosition, collection.size());
    }

    public void clearList() {
        mValues.clear();
    }

    // View holder
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemBinding listItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);
        BindingHolder bindingHolder = new BindingHolder(listItemBinding.getRoot());
        bindingHolder.setBinding(listItemBinding);

        return bindingHolder;
    }

    // bind data through view holder
    @Override
    public void onBindViewHolder(final BindingHolder holder, int position) {
        final TM value = mValues.get(position);
        holder.getBinding().setModelGeneral(value);
        holder.getBinding().setModelConfigImage(MainActivity.getsModelConfigImage(holder.getBinding().getRoot().getContext()));
        holder.getBinding().setIsDetail(String.valueOf(false));

        // transform genres list to strings
        String genreType = value.getGenreType();
        if (genreType != null) {
            List<Integer> genreList = value.getGenre_ids();
            String genreString = transferGenreListToString(holder.getBinding().getRoot().getContext(), genreType, genreList);
            holder.subtitle.setText(genreString);
        }

        if( value.getModelType().equals(ModelPeople.class.getName())){
            // send request to get biography
            String reqUrl = Constants.BASE_URL + Constants.CATE_PERSON + value.getId() + Constants.ATTACH_API_KEY_AS_FIRST_PARAM;
            NetworkRequest.instantiateClient().newCall(new Request.Builder().url(reqUrl).build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseStr = response.body().string();
                    try {
                        Log.i("LOG","bio = " + responseStr);
                        JSONObject jsonObject = new JSONObject(responseStr);
                        final String biography = jsonObject.getString(Constants.KEY_JSON_BIO);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                holder.description.setText(biography);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        runEnterAnimation(holder.getBinding().getRoot(), position);

        if (position >= getItemCount() - 1) {
            mOnLoadMoreListener.loadMore();
        }

        if( !(value instanceof ModelPeople)) {
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnListFragmentInteractionListener.onListFragmentInteraction(value);
                }
            });
        }
    }

    public String transferGenreListToString(Context context, String prefFile, List<Integer> genreList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        if (sharedPreferences == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        for (Integer genreId : genreList) {
            String genreString = sharedPreferences.getString(String.valueOf(genreId), "");
            if (genreString != null) stringBuilder.append(genreString + " ");
        }

        return stringBuilder.toString();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private ListItemBinding binding;
        private TextView subtitle;
        private TextView description;

        public BindingHolder(View itemView) {
            super(itemView);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            description = (TextView) itemView.findViewById(R.id.description);
        }

        public void setBinding(ListItemBinding binding) {
            this.binding = binding;
        }

        public ListItemBinding getBinding() {
            return binding;
        }
    }

    private void runEnterAnimation(View view, int position) {

        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastAnimatedPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_in_from_left);
            view.startAnimation(animation);
            lastAnimatedPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(BindingHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.getBinding().getRoot().clearAnimation();
    }
}
