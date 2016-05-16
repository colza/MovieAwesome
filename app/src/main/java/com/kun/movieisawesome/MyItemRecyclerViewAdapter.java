package com.kun.movieisawesome;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kun.movieisawesome.ItemFragment.OnListFragmentInteractionListener;
import com.kun.movieisawesome.databinding.ListItemBinding;
import com.kun.movieisawesome.dummy.DummyContent.DummyItem;
import com.kun.movieisawesome.model.ModelConfigImage;
import com.kun.movieisawesome.model.ModelGeneral;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter<TM extends ModelGeneral> extends RecyclerView.Adapter<MyItemRecyclerViewAdapter<TM>.BindingHolder> {

    private final List<TM> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final ItemFragment.OnLoadMoreListener onLoadMoreListener;
    private ModelConfigImage mModelConfigImage;


//    public MyItemRecyclerViewAdapter(List<TM> items, OnListFragmentInteractionListener listener, ModelConfigImage modelConfigImage) {
//        mValues = items;
//        mListener = listener;
//        mModelConfigImage = modelConfigImage;
//    }

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener, ItemFragment.OnLoadMoreListener onLoadMoreListener, ModelConfigImage modelConfigImage) {
        mValues = new ArrayList<>();
        mListener = listener;
        this.onLoadMoreListener = onLoadMoreListener;
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
        String imageUrl = mModelConfigImage.getBase_url() + mModelConfigImage.getPoster_sizes().get(4) + value.getShowImage();
        Picasso.with(holder.posterImage.getContext()).load(imageUrl).into(holder.posterImage);

        if( position > getItemCount() - 1 ){
            // load more data, return a list.
            onLoadMoreListener.loadMore();
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private ListItemBinding binding;
        private ImageView posterImage;

        public BindingHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.poster);

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
