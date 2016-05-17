package com.kun.movieisawesome;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.kun.movieisawesome.ItemFragment.OnListFragmentInteractionListener;
import com.kun.movieisawesome.databinding.ListItemBinding;
import com.kun.movieisawesome.dummy.DummyContent.DummyItem;
import com.kun.movieisawesome.model.ModelGeneral;

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
    private final OnListFragmentInteractionListener mOnListFragmentInteractionListener;
    private final ItemFragment.OnLoadMoreListener mOnLoadMoreListener;
    private int lastAnimatedPosition = -1;

    public MyItemRecyclerViewAdapter(OnListFragmentInteractionListener listener, ItemFragment.OnLoadMoreListener onLoadMoreListener) {
        mValues = new ArrayList<>();
        mOnListFragmentInteractionListener = listener;
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void attachCollections(Collection<TM> collection) {
        mValues.addAll(collection);
        notifyDataSetChanged();
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
    public void onBindViewHolder(BindingHolder holder, int position) {
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

        runEnterAnimation(holder.getBinding().getRoot(), position);

        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnListFragmentInteractionListener.onListFragmentInteraction(value);
            }
        });
        if (position >= getItemCount() - 1) {
            // load more data, return a list.
            mOnLoadMoreListener.loadMore();
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

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastAnimatedPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_in_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastAnimatedPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private ListItemBinding binding;
        private TextView subtitle;

        public BindingHolder(View itemView) {
            super(itemView);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        }

        public void setBinding(ListItemBinding binding) {
            this.binding = binding;
        }

        public ListItemBinding getBinding() {
            return binding;
        }
    }

    private void runEnterAnimation(View view, int position) {

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(view.getContext()));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }
}
