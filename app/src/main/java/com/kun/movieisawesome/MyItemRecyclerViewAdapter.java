package com.kun.movieisawesome;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.kun.movieisawesome.ItemFragment.OnListFragmentInteractionListener;
import com.kun.movieisawesome.databinding.ListItemBinding;
import com.kun.movieisawesome.dummy.DummyContent.DummyItem;
import com.kun.movieisawesome.model.ModelConfigImage;
import com.kun.movieisawesome.model.ModelGeneral;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter<TM extends ModelGeneral> extends RecyclerView.Adapter<MyItemRecyclerViewAdapter<TM>.BindingHolder> {
    private static JSONObject allGenreJsonObject;
    private final List<TM> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final ItemFragment.OnLoadMoreListener mOnLoadMoreListener;
    private ModelConfigImage mModelConfigImage;
    private int lastPosition = -1;



//    public MyItemRecyclerViewAdapter(List<TM> items, OnListFragmentInteractionListener listener, ModelConfigImage modelConfigImage) {
//        mValues = items;
//        mListener = listener;
//        mModelConfigImage = modelConfigImage;
//    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener, ItemFragment.OnLoadMoreListener onLoadMoreListener, ModelConfigImage modelConfigImage) {
        mValues = new ArrayList<>();
        mListener = listener;
        mOnLoadMoreListener = onLoadMoreListener;
        mModelConfigImage = modelConfigImage;
    }

    public void attachCollections(Collection<TM> collection){
        mValues.addAll(collection);
        notifyDataSetChanged();
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
    public void onBindViewHolder(BindingHolder holder, int position) {
        TM value = mValues.get(position);
        holder.getBinding().setModelGeneral(value);
        holder.getBinding().setModelConfigImage(mModelConfigImage);
        holder.getBinding().setIsDetail(false);

        String genreType = value.getGenreType();
        if( genreType != null ) {
            List<Integer> genreList = value.getGenre_ids();
            String genreString = transferGenreListToString(genreList, allGenreJsonObject != null ? allGenreJsonObject : initializeGenreObject(holder.subtitle.getContext(), genreType));
            holder.subtitle.setText(genreString);
        }

//        String imageUrl = mModelConfigImage.getBase_url() + mModelConfigImage.getPoster_sizes().get(3) + value.getShowImage();
//        Picasso.with(holder.posterImage.getContext()).load(imageUrl).into(holder.posterImage);
//        Picasso.with(holder.posterImage.getContext()).load((String)holder.posterImage.getTag()).into(holder.posterImage);

        setAnimation(holder.getBinding().getRoot(), position);

        if( position > getItemCount() - 1 ){
            // load more data, return a list.
            mOnLoadMoreListener.loadMore();
        }
    }

    private JSONObject initializeGenreObject(Context context, String genreType){
        String genreStringList = PreferenceManager.getDefaultSharedPreferences(context).getString(genreType,"");
        try {
            allGenreJsonObject = new JSONObject(genreStringList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return allGenreJsonObject;
    }

    public String transferGenreListToString(List<Integer> genreList, JSONObject genreJsonObject){
        if(genreJsonObject == null )
            return null;

        StringBuilder stringBuilder = new StringBuilder();
        for( Integer genreId : genreList){
            try {
                String genreString = genreJsonObject.getString(String.valueOf(genreId));
                if(genreString != null ) stringBuilder.append(genreString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private ListItemBinding binding;
        private TextView subtitle;
        private ImageView posterImage;

        public BindingHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.poster);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        }

        public void setBinding(ListItemBinding binding) {
            this.binding = binding;
        }

        public ListItemBinding getBinding() {
            return binding;
        }
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public DummyItem mItem;
//
//        public ViewHolder(ListItemBinding) {
//            super(view);
//            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
}
