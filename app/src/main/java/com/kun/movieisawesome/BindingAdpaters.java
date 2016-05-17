package com.kun.movieisawesome;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

/**
 * Created by TsaiKunYu on 17/05/16.
 */
public class BindingAdpaters {
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter({"imageUrl", "placeholder"})
    public static void loadImageWithError(ImageView view, String url, Drawable placeholder) {
        Picasso.with(view.getContext()).load(url).placeholder(placeholder).error(placeholder).into(view);
    }

    @BindingAdapter("relative_layout_rule")
    public static void setRelativeLayoutRule(View view, int rule) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.addRule(rule);
        Log.i("LOG","rule = " + rule);
        view.setLayoutParams(layoutParams);

    }

    @BindingAdapter({"relative_layout_rule_abc", "anchorId"})
    public static void setRelativeLayoutRuleToId(View view, int rule, int anchorId) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.addRule(rule, anchorId);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, float width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = Math.round(width);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, float height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = Math.round(height);
        view.setLayoutParams(layoutParams);
    }
}
